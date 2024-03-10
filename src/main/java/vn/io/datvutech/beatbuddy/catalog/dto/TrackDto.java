package vn.io.datvutech.beatbuddy.catalog.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Track", description = "Schema to hold Track information")
@Data
@NoArgsConstructor
public class TrackDto {
    @Schema(name = "id", description = "Unique identity of Track")
    private String id;

    @Schema(name = "urn", description = "Unique identity for resources in Beat Buddy")
    private String urn;

    @Schema(name = "name", description = "Name of track")
    private String name;

    @Schema(name = "durationSec", description = "Length of Track time")
    private Long durationSec;

    @Schema(name = "description", description = "Description about Track")
    private String description;

    @Schema(name = "releasedDate", description = "The full date, year with month or only year Track released")
    private String releasedDate;

    @Schema(name = "thumbnail", description = "Thumbnail image url of Track")
    private String thumbnail;

    @Schema(name = "isPublic", description = "Track is visible to the world or not")
    private Boolean isPublic;

    @Schema(name = "isPlayable", description = "Track could be playable or not")
    private Boolean isPlayable;

    private List<ArtistDto> artits;
}
