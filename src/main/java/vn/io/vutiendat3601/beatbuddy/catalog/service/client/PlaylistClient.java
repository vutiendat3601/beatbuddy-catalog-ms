package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PaginationDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistEditTrackItemDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistSnapshotDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ResponseDto;
import vn.io.vutiendat3601.beatbuddy.catalog.util.UserContext;

@Service
public class PlaylistClient {
    private final WebClient webClient;
    private final UserContext userContext;

    public PlaylistClient(
            @Value("${client.playlist.url}") String url,
            UserContext userContext

    ) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
        this.userContext = userContext;
    }

    public Mono<ResponseEntity<ResponseDto>> createPlaylist(PlaylistDto playlistDto) {
        return userContext.prepareUserContextHeader()
                .flatMap(userContextHeaders -> webClient
                        .post()
                        .uri("/v1/playlists")
                        .headers(headers -> headers.addAll(userContextHeaders))
                        .body(Mono.just(playlistDto), PlaylistDto.class)
                        .retrieve()
                        .toEntity(ResponseDto.class));
    }

    public Mono<ResponseEntity<PlaylistDto>> getPlaylist(String id) {
        return userContext.prepareUserContextHeader()
                .flatMap(userContextHeaders -> webClient
                        .get()
                        .uri("/v1/playlists/{id}", id)
                        .headers(headers -> headers.addAll(userContextHeaders))
                        .retrieve()
                        .toEntity(PlaylistDto.class));
    }

    public Mono<ResponseEntity<ResponseDto>> updatePlaylist(String id, PlaylistDto playlistDto) {
        return userContext.prepareUserContextHeader()
                .flatMap(userContextHeaders -> webClient
                        .put()
                        .uri("/v1/playlists/{id}", id)
                        .headers(headers -> headers.addAll(userContextHeaders))
                        .body(Mono.just(playlistDto), PlaylistDto.class)
                        .retrieve()
                        .toEntity(ResponseDto.class));
    }

    public Mono<ResponseEntity<ResponseDto>> addPlaylistTrackItems(
            String id,
            PlaylistEditTrackItemDto editTrackItemDto

    ) {
        return userContext.prepareUserContextHeader()
                .flatMap(userContextHeaders -> webClient
                        .put()
                        .uri("/v1/playlists/{id}/track-items", id)
                        .headers(headers -> headers.addAll(userContextHeaders))
                        .body(Mono.just(editTrackItemDto), PlaylistEditTrackItemDto.class)
                        .retrieve()
                        .toEntity(ResponseDto.class));
    }

    public Mono<ResponseEntity<ResponseDto>> removePlaylistTrackItems(
            String id,
            PlaylistEditTrackItemDto editTrackItemDto

    ) {
        return userContext.prepareUserContextHeader()
                .flatMap(userContextHeaders -> webClient
                        .method(HttpMethod.DELETE)
                        .uri("/v1/playlists/{id}/track-items", id)
                        .headers(headers -> headers.addAll(userContextHeaders))
                        .body(Mono.just(editTrackItemDto), PlaylistEditTrackItemDto.class)
                        .retrieve()
                        .toEntity(ResponseDto.class));
    }

    public Mono<ResponseEntity<PaginationDto<PlaylistSnapshotDto>>> getPlaylistSnapshots(String id, Integer page,
            Integer size) {
        return userContext.prepareUserContextHeader()
                .flatMap(userContextHeaders -> webClient
                        .get()
                        .uri("/v1/playlists/{id}/snapshots?page={page}&size={size}", id, page, size)
                        .headers(headers -> headers.addAll(userContextHeaders))
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<PaginationDto<PlaylistSnapshotDto>>() {
                        }));
    }
}
