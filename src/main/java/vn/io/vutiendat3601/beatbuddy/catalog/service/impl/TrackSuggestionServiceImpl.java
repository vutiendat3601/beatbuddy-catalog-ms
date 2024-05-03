package vn.io.vutiendat3601.beatbuddy.catalog.service.impl;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST_URN_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackSuggestionConstant.TRACK_SUGGESTION_CREATE_SCOPE;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackSuggestionConstant.TRACK_SUGGESTION_DELETE_SCOPE;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackSuggestionConstant.TRACK_SUGGESTION_ID_LENGTH;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackSuggestionConstant.TRACK_SUGGESTION_UPDATE_SCOPE;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackSuggestionConstant.TRACK_SUGGESTION_URI_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackSuggestionConstant.TRACK_SUGGESTION_URN_PREFIX;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackSuggestionConstant.TRACK_SUGGESTION_VIEW_SCOPE;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.keycloak.representations.idm.authorization.PermissionTicketRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.CreateTrackSuggestionDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackSuggestionDto;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.BadRequestException;
import vn.io.vutiendat3601.beatbuddy.catalog.service.TrackSuggestionService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.UserService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.KeycloakAuthzClient;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.TrackClient;
import vn.io.vutiendat3601.beatbuddy.catalog.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TrackSuggestionServiceImpl implements TrackSuggestionService {
  private final List<String> TRACK_SUGGESTION_OWNER_SCOPES =
      List.of(
          TRACK_SUGGESTION_VIEW_SCOPE,
          TRACK_SUGGESTION_CREATE_SCOPE,
          TRACK_SUGGESTION_UPDATE_SCOPE,
          TRACK_SUGGESTION_DELETE_SCOPE);

  private final TrackClient trackClient;
  private final UserService userService;
  private final KeycloakAuthzClient authzClient;

  @Override
  public Mono<URI> createTrackSuggestion(CreateTrackSuggestionDto createTrackSuggDto) {
    createTrackSuggDto.setId(StringUtils.randomString(TRACK_SUGGESTION_ID_LENGTH));
    return userService
        .getCurrentUser()
        .flatMap(
            userDto -> {
              createTrackSuggDto.setOwnerId(userDto.getId());
              return createTrackSuggestionResource(createTrackSuggDto)
                  .flatMap(
                      resource ->
                          grantTrackSuggestionResourcePermission(
                                  resource.getId(),
                                  TRACK_SUGGESTION_OWNER_SCOPES,
                                  userDto.getAuthUserId())
                              .thenReturn(resource))
                  .flatMap(
                      resource ->
                          trackClient
                              .createTrackSuggestion(createTrackSuggDto)
                              .map(ResponseEntity::getHeaders)
                              .map(HttpHeaders::getLocation));
            })
        .onErrorResume(
            WebClientResponseException.BadRequest.class,
            e -> Mono.error(new BadRequestException(BAD_REQUEST.getReasonPhrase())));
  }

  @Override
  public Mono<TrackSuggestionDto> getTrackSuggestion(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTrackSuggestion'");
  }

  private Mono<ResourceRepresentation> createTrackSuggestionResource(
      CreateTrackSuggestionDto createTrackSuggDto) {
    Set<ScopeRepresentation> scopes =
        TRACK_SUGGESTION_OWNER_SCOPES.stream()
            .map(ScopeRepresentation::new)
            .collect(Collectors.toSet());
    Set<String> uris = Set.of(TRACK_SUGGESTION_URI_PREFIX + "/" + createTrackSuggDto.getId());
    String type = TRACK_SUGGESTION_URN_PREFIX;
    ResourceRepresentation resourceRep = new ResourceRepresentation();
    resourceRep.setName(PLAYLIST_URN_PREFIX + ":" + createTrackSuggDto.getId());
    resourceRep.setDisplayName(createTrackSuggDto.getName());
    resourceRep.setType(type);
    resourceRep.setOwnerManagedAccess(true);
    resourceRep.setScopes(scopes);
    resourceRep.setUris(uris);
    return authzClient.createResource(resourceRep);
  }

  private Mono<Void> grantTrackSuggestionResourcePermission(
      String resourceId, List<String> scopes, String oauthUserId) {
    return Flux.fromIterable(scopes)
        .flatMap(
            scope -> {
              PermissionTicketRepresentation permissionTicketRep =
                  new PermissionTicketRepresentation();
              permissionTicketRep.setResource(resourceId);
              permissionTicketRep.setRequester(oauthUserId);
              permissionTicketRep.setScopeName(scope);
              permissionTicketRep.setGranted(true);
              return authzClient.createPermissionTicket(permissionTicketRep);
            })
        .thenEmpty(Mono.empty());
  }
}
