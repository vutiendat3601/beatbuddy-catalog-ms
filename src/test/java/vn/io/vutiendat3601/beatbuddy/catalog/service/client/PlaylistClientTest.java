package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;
import vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.CreatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ResponseDto;

@ActiveProfiles("dev")
@SpringBootTest
public class PlaylistClientTest {
  @CsvFileSource(resources = "/PlaylistClientTest-testCreatePlaylistCreated.csv")
  @ParameterizedTest
  public void testCreatePlaylistOk(
      String name, String thumbnail, String ownerId, Boolean isPublic, String description) {

    ResponseDto expectedRespBody =
        new ResponseDto(PlaylistConstant.STATUS_201, PlaylistConstant.MESSAGE_201);

    CreatePlaylistDto createPlaylistDto = new CreatePlaylistDto();
    createPlaylistDto.setName(name);
    createPlaylistDto.setOwnerId(ownerId);
    createPlaylistDto.setIsPublic(isPublic);
    createPlaylistDto.setDescription(description);

    StepVerifier.create(playlistClient.createPlaylist(createPlaylistDto))
        .expectNextMatches(resp -> resp.getBody().equals(expectedRespBody))
        .verifyComplete();
  }

  @Autowired
  public PlaylistClientTest(PlaylistClient playlistClient) {
    this.playlistClient = playlistClient;
  }

  private final PlaylistClient playlistClient;
}
