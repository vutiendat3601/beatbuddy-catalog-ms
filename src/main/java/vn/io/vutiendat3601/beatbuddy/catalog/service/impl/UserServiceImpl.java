package vn.io.vutiendat3601.beatbuddy.catalog.service.impl;

import static vn.io.vutiendat3601.beatbuddy.catalog.constant.UserConstant.USER;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UserDto;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.catalog.service.UserService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.UserClient;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserClient userClient;

  @Override
  public Mono<UserDto> getCurrentUser() {
    return userClient.getCurrentUser().map(ResponseEntity::getBody);
  }

  @Override
  public Mono<UserDto> getUser(String id) {
    return userClient
        .getUser(id)
        .map(ResponseEntity::getBody)
        .onErrorResume(
            WebClientResponseException.NotFound.class,
            e -> Mono.error(new ResourceNotFoundException(USER, "id", id)));
  }

  @Override
  public Mono<List<UserDto>> getSeveralUsers(List<String> ids) {
    return userClient.getSeveralUsers(ids).map(ResponseEntity::getBody);
  }
}
