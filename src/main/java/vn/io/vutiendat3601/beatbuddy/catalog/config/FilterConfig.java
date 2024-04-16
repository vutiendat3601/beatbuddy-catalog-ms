package vn.io.vutiendat3601.beatbuddy.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.WebFilter;
import vn.io.vutiendat3601.beatbuddy.catalog.constant.HeaderConstant;

@Configuration
public class FilterConfig {
  @Bean
  WebFilter userContextFilter() {
    return (exchange, chain) ->
        chain
            .filter(exchange)
            .contextWrite(
                ctx -> {
                  HttpHeaders headers = exchange.getRequest().getHeaders();
                  for (String header : HeaderConstant.HEADERS) {
                    String headerValue = headers.getFirst(header);
                    if (headerValue != null) {
                      ctx = ctx.put(header, headerValue);
                    }
                  }
                  return ctx;
                });
  }
}
