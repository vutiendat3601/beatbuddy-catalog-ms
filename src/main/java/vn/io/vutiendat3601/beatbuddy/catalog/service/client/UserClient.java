package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ResponseDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UserDto;
import vn.io.vutiendat3601.beatbuddy.catalog.util.UserContext;

@Service
public class UserClient {
  private final WebClient webClient;
  private final UserContext userContext;

  public UserClient(@Value("${client.user.url}") String url, UserContext userContext) {

    this.webClient = WebClient.builder().baseUrl(url).build();
    this.userContext = userContext;
  }

  public Mono<ResponseEntity<UserDto>> getCurrentUser() {
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .get()
                    .uri("/v1/users/me")
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .retrieve()
                    .toEntity(UserDto.class));
  }

  public Mono<ResponseEntity<UserDto>> getUser(String id) {
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .get()
                    .uri("/v1/users/{id}", id)
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .retrieve()
                    .toEntity(UserDto.class));
  }

  public Mono<ResponseEntity<List<UserDto>>> getSeveralUsers(List<String> ids) {
    final String uri =
        UriComponentsBuilder.fromUriString("/v1/users").queryParam("ids", ids).toUriString();
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .get()
                    .uri(uri)
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .retrieve()
                    .toEntityList(UserDto.class));
  }

  public Mono<ResponseEntity<ResponseDto>> updateCurrentUser(UserDto userDto) {
    return userContext
        .prepareUserContextHeader()
        .flatMap(
            userContextHeaders ->
                webClient
                    .put()
                    .uri("/v1/users/me")
                    .headers(headers -> headers.addAll(userContextHeaders))
                    .bodyValue(userDto)
                    .retrieve()
                    .toEntity(ResponseDto.class));
  }
}
