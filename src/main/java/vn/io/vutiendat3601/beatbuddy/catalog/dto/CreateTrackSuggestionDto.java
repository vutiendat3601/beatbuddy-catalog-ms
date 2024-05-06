package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Schema(name = "CreateTrackSuggestion", description = "Schema to hold Track Suggestion information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrackSuggestionDto {
  @Schema(name = "id", description = "Unique identity of Track Suggestion")
  private String id;

  @Schema(name = "name", description = "Name of Track Suggestion")
  @NotEmpty(message = "name is required")
  private String name;

  @Schema(name = "url", description = "URL of Track Suggestion")
  @NotNull(message = "url is required")
  @Pattern(regexp = "^(http|https)://.*$", message = "url must be a valid URL")
  private String url;

  @Schema(name = "description", description = "Description about Track Suggestion")
  private String description;

  @Schema(name = "thumbnail", description = "Thumbnail url of Track Suggestion")
  @Pattern(regexp = "^(http|https)://.*$", message = "url must be a valid URL")
  private String thumbnail;

  @Schema(name = "releasedDate", description = "Released date of Track Suggestion")
  private String releasedDate;

  @Schema(name = "artistIds", description = "List of Artist Ids of Track Suggestion")
  private List<
          @NotNull(message = "artistId is required")
          @Length(min = 16, max = 16, message = "OwnerId must be 16 characters") String>
      artistIds = new LinkedList<>();

  @Schema(name = "ownerId", description = "Owner Id of Track Suggestion")
  private String ownerId;
}
