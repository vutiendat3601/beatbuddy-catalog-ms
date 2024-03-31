package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackConstant.TRACK_DTO_LIST_REF;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;

@Service
public class TrackClient {
    private final WebClient webClient;

    public TrackClient(@Value("${client.track.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TrackDto> getTrack(String id) {
        return webClient
                .get()
                .uri("/v1/tracks/{id}", id)
                .retrieve()
                .bodyToMono(TrackDto.class);
    }

    public Mono<List<TrackDto>> getSeveralTracks(List<String> ids) {
        final String uri = UriComponentsBuilder.fromUriString("/v1/tracks")
                .queryParam("ids", ids)
                .toUriString();

        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(TRACK_DTO_LIST_REF);
    }
}
