package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Schema(name = "PlaylistTrackItem", description = "Schema to hold information of Track in Playlist")
@JsonIgnoreProperties(
    value = {"id", "addedById"},
    allowSetters = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrackItemDto {
  @NotNull(message = "Track ID is required")
  @Length(min = 16, max = 16, message = "Track ID must be 16 characters")
  @Schema(name = "id", description = "Unique identity of Track")
  private String id;

  @Schema(name = "track", description = "The Track information")
  private TrackDto track;

  @Schema(name = "addedById", description = "The ID of User who added this Track to Playlist")
  private String addedById;

  @Schema(name = "addedBy", description = "The User who added this Track to Playlist")
  private UserDto addedBy;
}
