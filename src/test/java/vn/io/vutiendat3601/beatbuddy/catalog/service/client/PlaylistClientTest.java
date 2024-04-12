package vn.io.vutiendat3601.beatbuddy.catalog.service.client;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import reactor.test.StepVerifier;
import vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ResponseDto;

@ActiveProfiles("dev")
@SpringBootTest
public class PlaylistClientTest {
    @CsvFileSource(resources = "/PlaylistClientTest-testCreatePlaylistCreated.csv")
    @ParameterizedTest
    public void testCreatePlaylistOk(
            String name,
            String thumbnail,
            String ownerId,
            Boolean isPublic,
            String description

    ) {
        ResponseDto expectedRespBody = new ResponseDto(PlaylistConstant.STATUS_201, PlaylistConstant.MESSAGE_201);

        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setName(name);
        playlistDto.setThumbnail(thumbnail);
        playlistDto.setOwnerId(ownerId);
        playlistDto.setIsPublic(isPublic);
        playlistDto.setDescription(description);

        StepVerifier
                .create(playlistClient.createPlaylist(playlistDto))
                .expectNextMatches(resp -> resp.getBody().equals(expectedRespBody))
                .verifyComplete();
    }

    // @CsvFileSource(resources = "/PlaylistClientTest-testUpdatePlaylistTrackItemsOk.csv", delimiter = '|')
    // @ParameterizedTest
    // public void testUpdatePlaylistTrackItemsOk(
    //         String id,
    //         String trackItemsJson

    // ) throws JsonMappingException, JsonProcessingException {
    //     ObjectMapper objMapper = new ObjectMapper();
    //     objMapper.findAndRegisterModules();
    //     ResponseDto expectedRespBody = new ResponseDto(PlaylistConstant.STATUS_200, PlaylistConstant.MESSAGE_200);
    //     List<PlaylistTrackItemDto> trackItems = objMapper.readValue(trackItemsJson, new TypeReference<>() {
    //     });
        
    //     StepVerifier
    //             .create(playlistClient.updatePlaylistTrackItems(id, trackItems))
    //             .expectNextMatches(resp -> resp.getBody().equals(expectedRespBody))
    //             .verifyComplete();
    // }

    @Autowired
    public PlaylistClientTest(PlaylistClient playlistClient) {
        this.playlistClient = playlistClient;
    }

    private final PlaylistClient playlistClient;
}
