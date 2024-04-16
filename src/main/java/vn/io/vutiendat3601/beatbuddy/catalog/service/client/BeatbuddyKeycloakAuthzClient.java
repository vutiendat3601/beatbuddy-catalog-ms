package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import lombok.AllArgsConstructor;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.PermissionTicketRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class BeatbuddyKeycloakAuthzClient {
  private final AuthzClient authzClient;

  public Mono<ResourceRepresentation> createResource(ResourceRepresentation resourceRep) {
    return Mono.justOrEmpty(authzClient.protection().resource().create(resourceRep));
  }

  public Mono<Void> updateResource(ResourceRepresentation resourceRep) {
    return Mono.fromRunnable(
            () -> {
              authzClient.protection().resource().update(resourceRep);
            })
        .thenEmpty(Mono.empty());
  }

  public Mono<PermissionTicketRepresentation> createPermissionTicket(
      PermissionTicketRepresentation permissionTicketRep) {

    return Mono.just(authzClient.protection().permission().create(permissionTicketRep))
        .onErrorResume(
            WebClientResponseException.BadRequest.class, e -> Mono.just(permissionTicketRep));
  }

  public Mono<ResourceRepresentation> getResourceById(String id) {
    return Mono.justOrEmpty(authzClient.protection().resource().findById(id));
  }

  public Mono<ResourceRepresentation> getResourceByName(String name) {
    return Mono.justOrEmpty(authzClient.protection().resource().findByName(name));
  }
}
