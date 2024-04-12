package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import java.util.LinkedList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Track", description = "Schema to hold Track information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackDto {
    @Schema(name = "id", description = "Unique identity of Track")
    private String id;

    @Schema(name = "urn", description = "Unique identity for resources in Beat Buddy")
    private String urn;

    @Schema(name = "name", description = "Name of track")
    private String name;

    @Schema(name = "durationSec", description = "Length of Track time")
    private Integer durationSec = 0;

    @Schema(name = "description", description = "Description about Track")
    private String description;

    @Schema(name = "releasedDate", description = "The full date, year with month or only year Track released")
    private String releasedDate;

    @Schema(name = "thumbnail", description = "Thumbnail image url of Track")
    private String thumbnail;

    @Schema(name = "isPublic", description = "Track is visible to the world or not")
    private Boolean isPublic = false;

    @Schema(name = "isPlayable", description = "Track could be playable or not")
    private Boolean isPlayable = false;

    @Schema(name = "totalViews", description = "Total views of Track page")
    private Long totalViews = 0L;

    @Schema(name = "totalLikes", description = "Total likes of Track")
    private Long totalLikes = 0L;

    @Schema(name = "totalShares", description = "Total shares of Track")
    private Long totalShares = 0L;

    @Schema(name = "totalListens", description = "Total listens of Track")
    private Long totalListens = 0L;

    @Schema(name = "artistIds", description = "Artist ids who perform Track")
    private List<String> artistIds = new LinkedList<>();
}
