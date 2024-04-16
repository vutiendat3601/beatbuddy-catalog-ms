package vn.io.vutiendat3601.beatbuddy.catalog.util;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.constant.HeaderConstant;

@Component
public class UserContext {
  public Mono<HttpHeaders> prepareUserContextHeader() {
    return Mono.deferContextual(
        ctx -> {
          HttpHeaders headers = new HttpHeaders();
          for (String header : HeaderConstant.HEADERS) {
            ctx.<String>getOrEmpty(header)
                .ifPresent(headerValue -> headers.set(header, headerValue));
          }
          return Mono.just(headers);
        });
  }
}
