package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "PlaylistSnapshot", description = "Schema to hold Playlist information")
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSnapshotDto {
    @Schema(name = "id", description = "Unique identity of Playlist")
    private String id;

    @Schema(name = "playlistId", description = "Playlist ID this snapshot belongs to")
    private String playlistId;

    @Schema(name = "numOfTracks", description = "Number of Track Items in Playlist Snapshot")
    private Integer numOfTracks = 0;

    @Schema(name = "durationSec", description = "Total time of snapshot in seconds")
    private Long durationSec = 0L;


    @Schema(name = "trackItems", description = "List of track items in snapshot")
    @NotNull
    private List<@Valid @NotNull PlaylistTrackItemDto> trackItems;
}
