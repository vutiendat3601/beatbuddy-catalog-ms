package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.CreatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ResponseDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistItemDto;
import vn.io.vutiendat3601.beatbuddy.catalog.util.UserContext;

@Service
public class PlaylistClient {
  private final WebClient webClient;
  private final UserContext userContext;

  public PlaylistClient(@Value("${client.playlist.url}") String url, UserContext userContext) {
    this.webClient = WebClient.builder().baseUrl(url).build();
    this.userContext = userContext;
  }

  public Mono<ResponseEntity<ResponseDto>> createPlaylist(CreatePlaylistDto createPlaylistDto) {
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .post()
                    .uri("/v1/playlists")
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .body(Mono.just(createPlaylistDto), CreatePlaylistDto.class)
                    .retrieve()
                    .toEntity(ResponseDto.class));
  }

  public Mono<ResponseEntity<PlaylistDto>> getPlaylist(String id) {
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .get()
                    .uri("/v1/playlists/{id}", id)
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .retrieve()
                    .toEntity(PlaylistDto.class));
  }

  public Mono<ResponseEntity<ResponseDto>> updatePlaylist(
      String id, UpdatePlaylistDto updatePlaylistDto) {
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .put()
                    .uri("/v1/playlists/{id}", id)
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .body(Mono.just(updatePlaylistDto), UpdatePlaylistDto.class)
                    .retrieve()
                    .toEntity(ResponseDto.class));
  }

  public Mono<ResponseEntity<ResponseDto>> addPlaylistTrackItems(
      String id, UpdatePlaylistItemDto addTrackItemDto) {
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .put()
                    .uri("/v1/playlists/{id}/items", id)
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .body(Mono.just(addTrackItemDto), UpdatePlaylistItemDto.class)
                    .retrieve()
                    .toEntity(ResponseDto.class));
  }

  public Mono<ResponseEntity<ResponseDto>> removePlaylistItems(
      String id, UpdatePlaylistItemDto removeTrackItemDto) {
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .method(HttpMethod.DELETE)
                    .uri("/v1/playlists/{id}/items", id)
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .body(Mono.just(removeTrackItemDto), UpdatePlaylistItemDto.class)
                    .retrieve()
                    .toEntity(ResponseDto.class));
  }
}
