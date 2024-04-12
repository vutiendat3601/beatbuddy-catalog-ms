package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PlaylistEditTrackItemDto {
    @Length(min = 32, max = 32, message = "snapshotId must be 32 characters", groups = { AddTrackItem.class, RemoveTrackItem.class })
    private String snapshotId;

    @Schema(name = "position", description = "Position of track items in playlist")
    @NotNull(groups = AddTrackItem.class)
    @PositiveOrZero(groups = AddTrackItem.class)
    private Integer position;

    @Schema(name = "addedBy", description = "who add track items to playlist")
    private String addedBy;

    @Schema(name = "trackItems", description = "List of track items to add in playlist")
    @NotNull(message = "trackItems is required", groups = AddTrackItem.class)
    private List<@Valid @NotNull PlaylistTrackItemDto> trackItems;

    @Schema(name = "trackIds", description = "List of track ids to remove from playlist")
    @NotEmpty(message = "trackIds is required", groups = RemoveTrackItem.class)
    private List<@NotNull String> trackIds;

    public static interface AddTrackItem {
    }

    public static interface RemoveTrackItem {
    }
}
