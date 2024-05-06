package vn.io.vutiendat3601.beatbuddy.catalog.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.authorization.client.AuthzClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@AllArgsConstructor
@Data
@ConfigurationProperties(prefix = "keycloak.authorization.service.client")
public class KeycloakAuthzServiceClientConfig {
  @JsonProperty("auth-server-url")
  private String authServerUrl;

  @JsonProperty("realm")
  private String realm;

  @JsonProperty("client-id")
  private String clientId;

  @JsonProperty("client-secret")
  private String clientSecret;

  @Bean
  AuthzClient keycloakAuthzServiceClient() {
    return AuthzClient.create(
        new org.keycloak.authorization.client.Configuration(
            authServerUrl, realm, clientId, Map.of("secret", clientSecret), null));
  }
}
