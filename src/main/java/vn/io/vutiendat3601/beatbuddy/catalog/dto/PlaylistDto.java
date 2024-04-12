package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Playlist", description = "Schema to hold Playlist information")
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDto {
    @Schema(name = "id", description = "Unique identity of Playlist")
    @NotNull(message = "Id is required", groups = CreatePlaylist.class)
    @Length(min = 16, max = 16, message = "Id must be 16 characters")
    private String id;

    @Schema(name = "name", description = "Name of Playlist")
    @NotEmpty(message = "Name is required", groups = { CreatePlaylist.class, UpdatePlaylist.class })
    private String name;

    @Schema(name = "thumbnail", description = "Thumbnail image url of Playlist")
    private String thumbnail;

    @Schema(name = "durationSec", description = "Length of Playlist time")
    private Long durationSec = 0L;

    @Schema(name = "description", description = "Description about Playlist")
    private String description;

    @Schema(name = "isPublic", description = "Playlist is visible to the world or not")
    @NotNull(message = "isPublic is required", groups = { CreatePlaylist.class, UpdatePlaylist.class })
    private Boolean isPublic = true;

    @Schema(name = "isCollaborative", description = "Playlist could be collaborative by others or not")
    private Boolean isCollaborative = false;

    @Schema(name = "totalViews", description = "Total views of Playlist page")
    private Long totalViews = 0L;

    @Schema(name = "totalLikes", description = "Total likes of Playlist")
    private Long totalLikes = 0L;

    @Schema(name = "totalShares", description = "Total shares of Playlist")
    private Long totalShares = 0L;

    @Schema(name = "snapshotId", description = "Current states of Playlist")
    @Length(min = 32, max = 32, message = "SnapshotId must be 32 characters", groups = UpdatePlaylist.class)
    private String snapshotId;

    @Length(min = 16, max = 16, message = "OwnerId must be 16 characters")
    private String ownerId;

    @Schema(name = "trackItems", description = "List of tracks in Playlist")
    private List<PlaylistTrackItemDto> trackItems = new LinkedList<>();

    @Schema(name = "authResourceId", description = "Resource id of Playlist in Auth server")
    private String authResourceId;

    public static interface CreatePlaylist {
    }

    public static interface UpdatePlaylist {
    }
}
