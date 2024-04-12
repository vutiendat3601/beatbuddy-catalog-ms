package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.test.StepVerifier;

@SpringBootTest
public class ArtistClientTest {

    @Test
    public void testGetTrackBadRequest() {
        StepVerifier.create(artistClient.getArtist("null"))
                .expectError(WebClientResponseException.BadRequest.class)
                .verify();
    }

    @Test
    public void testGetTrackOk() {
        String artistId = "9RszPgJtO8WXEBE5";
        StepVerifier.create(artistClient.getArtist(artistId))
                .expectNextMatches(resp -> resp.getBody().getId().equals(artistId))
                .verifyComplete();
    }

    @Test
    public void testgetSeveralArtists() {
        List<String> artistIds = List.of("9RszPgJtO8WXEBE5", "F2nkWsRXi7Ox7DpK");
        StepVerifier.create(artistClient.getSeveralArtists(artistIds))
                .expectNextMatches(resp -> resp.getBody().size() == 2)
                .verifyComplete();
    }

    @Autowired
    public ArtistClientTest(ArtistClient artistClient) {
        this.artistClient = artistClient;
    }

    private final ArtistClient artistClient;
}
