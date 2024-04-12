package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "PlaylistTrackItem", description = "Schema to hold information of Track in Playlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrackItemDto {
    @NotNull(message = "Track ID is required")
    @Length(min = 16, max = 16, message = "Track ID must be 16 characters")
    @Schema(name = "trackId", description = "Unique identity of Track")
    private String trackId;

    @Schema(name = "durationSec", description = "Length of Track time")
    private Integer durationSec = 0;

    @Schema(name = "addedBy", description = "The ID of User who added this Track to Playlist")
    private String addedBy;
}
