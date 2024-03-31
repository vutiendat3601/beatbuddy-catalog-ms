package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import static vn.io.vutiendat3601.beatbuddy.catalog.constant.ArtistConstant.ARTIST_DTO_LIST_REF;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ArtistDto;

@Service
public class ArtistClient {
    private final WebClient webClient;

    public ArtistClient(@Value("${client.artist.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ArtistDto> getArtist(String id) {
        return webClient
                .get()
                .uri("/v1/artists/{id}", id)
                .retrieve()
                .bodyToMono(ArtistDto.class);
    }

    public Mono<List<ArtistDto>> getSeveralArtists(List<String> ids) {
        final String uri = UriComponentsBuilder.fromUriString("/v1/artists")
                .queryParam("ids", ids)
                .toUriString();

        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ARTIST_DTO_LIST_REF);
    }
}
