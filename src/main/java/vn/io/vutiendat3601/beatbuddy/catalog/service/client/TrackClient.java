package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;
import vn.io.vutiendat3601.beatbuddy.catalog.util.UserContext;

@Service
public class TrackClient {
    private final WebClient webClient;
    private final UserContext userContext;

    public TrackClient(
            @Value("${client.track.url}") String url,
            UserContext userContext

    ) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
        this.userContext = userContext;
    }

    public Mono<ResponseEntity<TrackDto>> getTrackById(String id) {
        return userContext.prepareUserContextHeader()
                .flatMap(userContextHeaders -> webClient
                        .get()
                        .uri("/v1/tracks/{id}", id)
                        .headers(headers -> headers.addAll(userContextHeaders))
                        .retrieve()
                        .toEntity(TrackDto.class));
    }

    public Mono<ResponseEntity<List<TrackDto>>> getSeveralTracks(List<String> ids) {
        final String uri = UriComponentsBuilder.fromUriString("/v1/tracks")
                .queryParam("ids", ids)
                .toUriString();

        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<TrackDto>>() {
                });
    }
}
