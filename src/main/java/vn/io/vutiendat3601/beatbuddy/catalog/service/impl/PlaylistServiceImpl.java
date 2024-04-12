package vn.io.vutiendat3601.beatbuddy.catalog.service.impl;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST_ID_LENGTH;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST_URI_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST_URN_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistScope.PLAYLIST_DELETE;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistScope.PLAYLIST_EDIT;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistScope.PLAYLIST_VIEW;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistScope.PLAYLIST_VIEW_PUBLIC;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PaginationDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistEditTrackItemDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistSnapshotDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistTrackItemDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UserDto;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.BadRequestException;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.catalog.service.PlaylistService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.BeatbuddyKeycloakAuthzClient;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.PlaylistClient;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.TrackClient;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.UserClient;
import vn.io.vutiendat3601.beatbuddy.catalog.util.StringUtil;

@RequiredArgsConstructor
@Service
public class PlaylistServiceImpl implements PlaylistService {
    private static final ScopeRepresentation[] PUBLIC_PLAYLIST_SCOPES = {
            PLAYLIST_VIEW_PUBLIC.instance(),
            PLAYLIST_EDIT.instance(),
            PLAYLIST_DELETE.instance()
    };

    private static final ScopeRepresentation[] COLLABORATIVE_PLAYLIST_SCOPES = {
            PLAYLIST_VIEW.instance(),
            PLAYLIST_EDIT.instance(),
            PLAYLIST_DELETE.instance()
    };

    private final TrackClient trackClient;
    private final PlaylistClient playlistClient;
    private final UserClient userClient;
    private final BeatbuddyKeycloakAuthzClient authzClient;

    @Override
    public Mono<URI> createPlaylist(PlaylistDto playlistDto) {
        playlistDto.setId(StringUtil.randomString(PLAYLIST_ID_LENGTH));
        playlistDto.setIsCollaborative(!playlistDto.getIsPublic());
        return userClient.getCurrentUser()
                .map(ResponseEntity::getBody)
                .flatMap(userDto -> {
                    playlistDto.setOwnerId(userDto.getId());
                    return createPlaylistResource(playlistDto, userDto)
                            .flatMap(resource -> {
                                playlistDto.setAuthResourceId(resource.getId());
                                return playlistClient.createPlaylist(playlistDto)
                                        .map(ResponseEntity::getHeaders)
                                        .map(headers -> headers.getLocation());
                            });
                })
                .onErrorResume(WebClientResponseException.BadRequest.class,
                        e -> Mono.error(new BadRequestException(BAD_REQUEST.getReasonPhrase())));
    }

    @Override
    public Mono<PlaylistDto> getPlaylist(String id) {
        return playlistClient.getPlaylist(id)
                .map(ResponseEntity::getBody)
                .onErrorResume(
                        WebClientResponseException.NotFound.class,
                        e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id))

                );
    }

    @Override
    public Mono<PaginationDto<PlaylistSnapshotDto>> getPlaylistSnapshots(String id, int page, int size) {
        return playlistClient.getPlaylistSnapshots(id, page, size)
                .map(ResponseEntity::getBody)
                .onErrorResume(
                        WebClientResponseException.NotFound.class,
                        e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id))

                );
    }

    @Override
    public Mono<Void> updatePlaylist(String id, PlaylistDto playlistDto) {
        playlistDto.setIsCollaborative(!playlistDto.getIsPublic());
        return playlistClient.updatePlaylist(id, playlistDto)
                .flatMap(resp -> {
                    return getPlaylist(id)
                            .flatMap(this::updatePlaylistResource);
                })
                .thenEmpty(Mono.empty())
                .onErrorResume(
                        WebClientResponseException.NotFound.class,
                        e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id))

                );
    }

    @Override
    public Mono<Void> addPlaylistTrackItems(String id, PlaylistEditTrackItemDto editTrackItemDto) {
        Mono<Map<String, TrackDto>> trackDtosMono = getTrackDtos(
                editTrackItemDto.getTrackItems().stream().map(ti -> ti.getTrackId()).toList());
        return trackDtosMono.flatMap(trackDtosMap -> {
            // ## Get durationSec for Track Item
            for (PlaylistTrackItemDto trackItemDto : editTrackItemDto.getTrackItems()) {
                TrackDto trackDto = trackDtosMap.get(trackItemDto.getTrackId());
                if (trackDto != null) {
                    trackItemDto.setDurationSec(trackDto.getDurationSec());
                }
            }
            return userClient.getCurrentUser()
                    .map(ResponseEntity::getBody)
                    .flatMap(userDto -> {
                        editTrackItemDto.setAddedBy(userDto.getId());
                        return playlistClient.addPlaylistTrackItems(id, editTrackItemDto)
                                .thenEmpty(Mono.empty())
                                .onErrorResume(
                                        WebClientResponseException.NotFound.class,
                                        e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id)))
                                .onErrorResume(WebClientResponseException.BadRequest.class,
                                        e -> Mono.error(new BadRequestException(BAD_REQUEST.getReasonPhrase())));
                    });
        });
    }

    @Override
    public Mono<Void> removePlaylistTrackItems(String id, PlaylistEditTrackItemDto editTrackItemDto) {
        return userClient.getCurrentUser()
                .map(ResponseEntity::getBody)
                .flatMap(userDto -> playlistClient
                        .removePlaylistTrackItems(id, editTrackItemDto)
                        .thenEmpty(Mono.empty())
                        .onErrorResume(
                                WebClientResponseException.NotFound.class,
                                e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id)))
                        .onErrorResume(WebClientResponseException.BadRequest.class,
                                e -> Mono.error(new BadRequestException(BAD_REQUEST.getReasonPhrase())))

                );
    }

    private Mono<Map<String, TrackDto>> getTrackDtos(List<String> trackIds) {
        return trackClient.getSeveralTracks(trackIds)
                .map(ResponseEntity::getBody)
                .map(trackDtos -> trackDtos.stream().collect(
                        Collectors.toMap(TrackDto::getId, trackDto -> trackDto)));
    }

    private Mono<ResourceRepresentation> createPlaylistResource(PlaylistDto playlistDto, UserDto userDto) {
        Set<ScopeRepresentation> scopes = playlistDto.getIsPublic() ? Set.of(PUBLIC_PLAYLIST_SCOPES)
                : Set.of(COLLABORATIVE_PLAYLIST_SCOPES);
        Set<String> uris = Set.of(
                PLAYLIST_URI_PREFIX + "/" + playlistDto.getId(),
                PLAYLIST_URI_PREFIX + "/" + playlistDto.getId() + "/*");
        ResourceRepresentation resourceRep = new ResourceRepresentation();
        resourceRep.setName(PLAYLIST_URN_PREFIX + ":" + playlistDto.getId());
        resourceRep.setDisplayName(playlistDto.getName());
        resourceRep.setOwnerManagedAccess(true);
        resourceRep.setOwner(userDto.getAuthUserId());
        resourceRep.setScopes(scopes);
        resourceRep.setUris(uris);
        resourceRep.setType(PLAYLIST_URN_PREFIX);
        return authzClient.createResource(resourceRep);
    }

    private Mono<Void> updatePlaylistResource(PlaylistDto playlistDto) {
        Set<ScopeRepresentation> scopes = playlistDto.getIsPublic() ? Set.of(PUBLIC_PLAYLIST_SCOPES)
                : Set.of(COLLABORATIVE_PLAYLIST_SCOPES);
        Set<String> uris = Set.of(
                PLAYLIST_URI_PREFIX + "/" + playlistDto.getId(),
                PLAYLIST_URI_PREFIX + "/" + playlistDto.getId() + "/*");
        return authzClient.getResourceById(playlistDto.getAuthResourceId())
                .flatMap(resource -> {
                    resource.setScopes(scopes);
                    resource.setDisplayName(playlistDto.getName());
                    resource.setUris(uris);
                    return authzClient.updateResource(resource);
                });
    }
}
