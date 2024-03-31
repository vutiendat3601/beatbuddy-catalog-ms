package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.test.StepVerifier;

@SpringBootTest
public class TrackClientTest {
    @Test
    public void testGetTrackBadRequest() {
        StepVerifier.create(trackClient.getTrack("null"))
                .expectError(WebClientResponseException.BadRequest.class)
                .verify();
    }

    @Test
    public void testGetTrackOK() {
        String trackId = "5Wl4U9AkACejStTX";
        StepVerifier.create(trackClient.getTrack(trackId))
                .expectNextMatches(trackDto -> trackDto.getId().equals(trackId))
                .verifyComplete();
    }

    @Test
    public void testgetSeveralArtists() {
        List<String> trackIds = List.of("82id8db50gXsm6zX", "VXPDiEtJ0MyJ3HO7");
        StepVerifier.create(trackClient.getSeveralTracks(trackIds))
                .expectNextMatches(artistDtos -> artistDtos.size() == 2)
                .verifyComplete();
    }

    @Autowired
    public TrackClientTest(TrackClient trackClient) {
        this.trackClient = trackClient;
    }

    private final TrackClient trackClient;
}
