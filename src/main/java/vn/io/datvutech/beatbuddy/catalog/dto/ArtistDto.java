package vn.io.datvutech.beatbuddy.catalog.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Track", description = "Schema to hold Track information")
@Data
@NoArgsConstructor
public class ArtistDto {
    @Schema(name = "id", description = "Unique identity of Artist")
    private String id;

    @Schema(name = "urn", description = "Unique identity for resources in Beat Buddy")
    private String urn;

    @Schema(name = "name", description = "Name of track")
    private String name;

    @Schema(name = "isVerified", description = "Artist is verified information or not")
    private Boolean isVerified;

    @Schema(name = "isPublic", description = "Artist is visible to the world or not")
    private Boolean isPublic;

    @Schema(name = "realName", description = "Real name in the real life of Artist")
    private String realName;

    @Schema(name = "birthDate", description = "Birth date of Artist")
    private LocalDate birthDate;

    @Schema(name = "description", description = "Description about Artist")
    private String description;

    @Schema(name = "nationality", description = "Artist nationality")
    private String nationality;

    @Schema(name = "biography", description = "Biography introdution of Artist")
    private String biography;

    @Schema(name = "thumbnail", description = "Thumbnail image url of Artist")
    private String thumbnail;

    @Schema(name = "background", description = "Background image url of Artist")
    private String background;

    @Schema(name = "totalViews", description = "Total views of Artist page")
    private Long totalViews;

    @Schema(name = "totalLikes", description = "Total likes of Artist")
    private Long totalLikes;

    @Schema(name = "totalShares", description = "Total shares of Artist")
    private Long totalShares;
}
