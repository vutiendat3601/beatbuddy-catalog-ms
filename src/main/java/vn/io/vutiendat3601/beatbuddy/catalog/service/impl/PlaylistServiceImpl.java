package vn.io.vutiendat3601.beatbuddy.catalog.service.impl;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST_ID_LENGTH;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST_URI_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST_URN_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistScope.PLAYLIST_DELETE;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistScope.PLAYLIST_EDIT;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistScope.PLAYLIST_VIEW;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistScope.PLAYLIST_VIEW_PUBLIC;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackConstant.TRACK_URN_PREFIX;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.authorization.PermissionTicketRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.CreatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistItemDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UserDto;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.BadRequestException;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.catalog.service.PlaylistService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.TrackService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.UserService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.BeatbuddyKeycloakAuthzClient;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.PlaylistClient;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.TrackClient;
import vn.io.vutiendat3601.beatbuddy.catalog.util.StringUtil;

@RequiredArgsConstructor
@Service
public class PlaylistServiceImpl implements PlaylistService {
  private static final List<String> PUBLIC_PLAYLIST_SCOPES =
      List.of(PLAYLIST_VIEW_PUBLIC, PLAYLIST_EDIT, PLAYLIST_DELETE);

  private static final List<String> COLLABORATIVE_PLAYLIST_SCOPES =
      List.of(PLAYLIST_VIEW, PLAYLIST_EDIT, PLAYLIST_DELETE);

  private final TrackClient trackClient;
  private final TrackService trackService;
  private final PlaylistClient playlistClient;
  private final UserService userService;
  private final BeatbuddyKeycloakAuthzClient authzClient;

  @Override
  public Mono<URI> createPlaylist(CreatePlaylistDto createPlaylistDto) {
    createPlaylistDto.setId(StringUtil.randomString(PLAYLIST_ID_LENGTH));
    return userService
        .getCurrentUser()
        .flatMap(
            userDto -> {
              createPlaylistDto.setOwnerId(userDto.getId());
              final List<String> scopes =
                  createPlaylistDto.getIsPublic()
                      ? PUBLIC_PLAYLIST_SCOPES
                      : COLLABORATIVE_PLAYLIST_SCOPES;
              return createPlaylistResource(createPlaylistDto)
                  .flatMap(
                      resource -> {
                        return grantPlaylistResourcePermission(
                                resource.getId(), scopes, userDto.getAuthUserId())
                            .thenReturn(resource);
                      })
                  .flatMap(
                      resource ->
                          playlistClient
                              .createPlaylist(createPlaylistDto)
                              .map(ResponseEntity::getHeaders)
                              .map(headers -> headers.getLocation()));
            })
        .onErrorResume(
            WebClientResponseException.BadRequest.class,
            e -> Mono.error(new BadRequestException(BAD_REQUEST.getReasonPhrase())));
  }

  @Override
  public Mono<PlaylistDto> getPlaylist(String id) {
    final int MAX_NUM_OF_ITEMS = 50;
    return playlistClient
        .getPlaylist(id)
        .map(ResponseEntity::getBody)
        .flatMap(
            playlistDto -> {
              final List<String> userIds =
                  Stream.concat(
                          playlistDto.getItems().stream().map(i -> i.getAddedById()),
                          Stream.of(playlistDto.getOwnerId()))
                      .distinct()
                      .toList();
              final List<String> trackIds =
                  playlistDto.getItems().stream()
                      .filter(t -> t.getUrn().startsWith(TRACK_URN_PREFIX))
                      .map(t -> t.getUrn().substring(TRACK_URN_PREFIX.length() + 1))
                      .distinct()
                      .limit(MAX_NUM_OF_ITEMS)
                      .toList();
              Mono<Map<String, UserDto>> userDtosMapMono =
                  userService
                      .getSeveralUsers(userIds)
                      .map(
                          userDtos ->
                              userDtos.stream().collect(Collectors.toMap(UserDto::getId, u -> u)));
              Mono<Map<String, TrackDto>> trackDtosMapMono =
                  trackService
                      .getSeveralTracks(trackIds)
                      .map(
                          trackDtos ->
                              trackDtos.stream()
                                  .collect(Collectors.toMap(TrackDto::getUrn, t -> t)));
              return Flux.zip(userDtosMapMono, trackDtosMapMono)
                  .doOnNext(
                      tuple -> {
                        Map<String, UserDto> userDtosMap = tuple.getT1();
                        Map<String, TrackDto> trackDtosMap = tuple.getT2();
                        playlistDto.setOwner(userDtosMap.get(playlistDto.getOwnerId()));
                        playlistDto
                            .getItems()
                            .forEach(i -> i.setAddedBy(userDtosMap.get(i.getAddedById())));
                        playlistDto
                            .getItems()
                            .forEach(i -> i.setTrack(trackDtosMap.get(i.getUrn())));
                      })
                  .subscribeOn(Schedulers.parallel())
                  .then(Mono.just(playlistDto));
            })
        .onErrorResume(
            WebClientResponseException.NotFound.class,
            e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id)));
  }

  @Override
  public Mono<Void> updatePlaylist(String id, UpdatePlaylistDto updatePlaylistDto) {
    return playlistClient
        .updatePlaylist(id, updatePlaylistDto)
        .flatMap(
            resp -> {
              return playlistClient
                  .getPlaylist(id)
                  .map(ResponseEntity::getBody)
                  .flatMap(this::updatePlaylistResource);
            })
        .thenEmpty(Mono.empty())
        .onErrorResume(
            WebClientResponseException.NotFound.class,
            e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id)));
  }

  @Override
  public Mono<Void> addPlaylistItems(String id, UpdatePlaylistItemDto addPlaylistItemDto) {
    List<String> trackIds =
        addPlaylistItemDto.getItemUrns().stream()
            .filter(urn -> urn.startsWith(TRACK_URN_PREFIX))
            .map(urn -> urn.substring(TRACK_URN_PREFIX.length() + 1))
            .toList();
    Mono<List<TrackDto>> trackDtosMono =
        trackClient.getSeveralTracks(trackIds).map(ResponseEntity::getBody);
    Mono<UserDto> addedByMono = userService.getCurrentUser();
    return Flux.zip(trackDtosMono, addedByMono)
        .flatMap(
            tuple -> {
              List<TrackDto> trackDtos = tuple.getT1();
              if (trackDtos.size() != trackIds.size()) {
                return Mono.error(new ResourceNotFoundException("Some Tracks not found"));
              }
              UserDto addedBy = tuple.getT2();
              addPlaylistItemDto.setAddedById(addedBy.getId());
              return playlistClient
                  .addPlaylistTrackItems(id, addPlaylistItemDto)
                  .onErrorResume(
                      WebClientResponseException.NotFound.class,
                      e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id)))
                  .onErrorResume(
                      WebClientResponseException.BadRequest.class,
                      e -> Mono.error(new BadRequestException(BAD_REQUEST.getReasonPhrase())));
            })
        .subscribeOn(Schedulers.parallel())
        .thenEmpty(Mono.empty());
  }

  @Override
  public Mono<Void> removePlaylistItems(String id, UpdatePlaylistItemDto removePlaylistItemDto) {
    return userService
        .getCurrentUser()
        .flatMap(
            userDto ->
                playlistClient
                    .removePlaylistItems(id, removePlaylistItemDto)
                    .thenEmpty(Mono.empty())
                    .onErrorResume(
                        WebClientResponseException.NotFound.class,
                        e -> Mono.error(new ResourceNotFoundException(PLAYLIST, "id", id)))
                    .onErrorResume(
                        WebClientResponseException.BadRequest.class,
                        e -> Mono.error(new BadRequestException(BAD_REQUEST.getReasonPhrase()))));
  }

  private Mono<ResourceRepresentation> createPlaylistResource(CreatePlaylistDto createPlaylistDto) {
    Set<ScopeRepresentation> scopes =
        createPlaylistDto.getIsPublic()
            ? PUBLIC_PLAYLIST_SCOPES.stream()
                .map(ScopeRepresentation::new)
                .collect(Collectors.toSet())
            : COLLABORATIVE_PLAYLIST_SCOPES.stream()
                .map(ScopeRepresentation::new)
                .collect(Collectors.toSet());
    Set<String> uris =
        Set.of(
            PLAYLIST_URI_PREFIX + "/" + createPlaylistDto.getId(),
            PLAYLIST_URI_PREFIX + "/" + createPlaylistDto.getId() + "/*");
    ResourceRepresentation resourceRep = new ResourceRepresentation();
    resourceRep.setName(PLAYLIST_URN_PREFIX + ":" + createPlaylistDto.getId());
    resourceRep.setDisplayName(createPlaylistDto.getName());
    resourceRep.setType(PLAYLIST_URN_PREFIX);
    resourceRep.setOwnerManagedAccess(true);
    resourceRep.setScopes(scopes);
    resourceRep.setUris(uris);
    return authzClient.createResource(resourceRep);
  }

  private Mono<Void> grantPlaylistResourcePermission(
      String resourceId, List<String> scopes, String oauthUserId) {
    return Flux.fromIterable(scopes)
        .flatMap(
            scope -> {
              PermissionTicketRepresentation permissionTicketRep =
                  new PermissionTicketRepresentation();
              permissionTicketRep.setResource(resourceId);
              permissionTicketRep.setRequester(oauthUserId);
              permissionTicketRep.setScopeName(scope);
              permissionTicketRep.setGranted(true);
              return authzClient.createPermissionTicket(permissionTicketRep);
            })
        .thenEmpty(Mono.empty());
  }

  private Mono<Void> updatePlaylistResource(PlaylistDto playlistDto) {
    Set<ScopeRepresentation> scopes =
        playlistDto.getIsPublic()
            ? PUBLIC_PLAYLIST_SCOPES.stream()
                .map(ScopeRepresentation::new)
                .collect(Collectors.toSet())
            : COLLABORATIVE_PLAYLIST_SCOPES.stream()
                .map(ScopeRepresentation::new)
                .collect(Collectors.toSet());
    Set<String> uris =
        Set.of(
            PLAYLIST_URI_PREFIX + "/" + playlistDto.getId(),
            PLAYLIST_URI_PREFIX + "/" + playlistDto.getId() + "/*");
    return authzClient
        .getResourceByName(playlistDto.getUrn())
        .flatMap(
            resource -> {
              resource.setScopes(scopes);
              resource.setDisplayName(playlistDto.getName());
              resource.setUris(uris);
              return authzClient.updateResource(resource);
            });
  }
}
