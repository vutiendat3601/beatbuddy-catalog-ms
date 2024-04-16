package vn.io.vutiendat3601.beatbuddy.catalog.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.ResourceNotFoundException;

@ActiveProfiles("dev")
@SpringBootTest
public class TrackServiceTest {

  @Test
  public void testGetTrackDetailByIdNotFound() {
    String id = "K0l3eNFUCPGF3Sbz";
    StepVerifier.create(trackService.getTrack(id)).expectError(ResourceNotFoundException.class);
  }

  @Test
  public void testGetSeveralTrackDetails() {
    List<String> trackIds =
        List.of("K0l3eNFUCPGF3SbZ", "K0l3eNFUCPGF3SbZ", "xc", "xc", "ANfseNrP5DPz62aq");
    StepVerifier.create(trackService.getSeveralTracks(trackIds))
        .expectNextMatches(trackDetailDtos -> trackDetailDtos.size() == 2)
        .verifyComplete();
  }

  @Autowired
  public TrackServiceTest(TrackService trackService) {
    this.trackService = trackService;
  }

  private final TrackService trackService;
}
