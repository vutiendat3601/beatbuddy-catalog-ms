package vn.io.vutiendat3601.beatbuddy.catalog.service;

import java.util.List;

import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UserDto;

public interface UserService {
  Mono<UserDto> getCurrentUser();

  Mono<UserDto> getUser(String id);
  
  Mono<List<UserDto>> getSeveralUsers(List<String> ids);
}
