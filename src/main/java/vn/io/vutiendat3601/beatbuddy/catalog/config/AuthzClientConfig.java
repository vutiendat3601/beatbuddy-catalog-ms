package vn.io.vutiendat3601.beatbuddy.catalog.config;

import java.util.Map;

import org.keycloak.authorization.client.AuthzClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthzClientConfig {
    @Bean
    AuthzClient beatbuddyKeycloakAuthzClientBean(
            @Value("${keycloak.client.beatbuddy.authServerUrl}") String authServerUrl,
            @Value("${keycloak.client.beatbuddy.realm}") String realm,
            @Value("${keycloak.client.beatbuddy.clientId}") String clientId,
            @Value("${keycloak.client.beatbuddy.clientSecret}") String clientSecret

    ) {
        org.keycloak.authorization.client.Configuration conf = new org.keycloak.authorization.client.Configuration(authServerUrl, realm, clientId,
                Map.of("secret", clientSecret), null);
        return AuthzClient.create(conf);
    }
}
