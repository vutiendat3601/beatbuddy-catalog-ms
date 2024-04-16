package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "UpdatePlaylist", description = "Schema to hold Playlist information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlaylistDto {
  @Schema(name = "name", description = "Name of Playlist")
  @NotEmpty(message = "Name is required")
  private String name;

  @Schema(name = "description", description = "Description about Playlist")
  private String description;

  @Schema(name = "isPublic", description = "Playlist is visible to the world or not")
  @NotNull(message = "isPublic is required")
  private Boolean isPublic = true;
}
