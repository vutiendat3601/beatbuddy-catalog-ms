package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Schema(name = "Playlist", description = "Schema to hold Playlist information")
@JsonIgnoreProperties(
    value = {"ownerId"},
    allowSetters = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDto {
  @Schema(name = "id", description = "Unique identity of Playlist")
  private String id;

  @Schema(name = "urn", description = "Unique resource name of Playlist")
  private String urn;

  @Schema(name = "name", description = "Name of Playlist")
  private String name;

  @Schema(name = "thumbnail", description = "Thumbnail image url of Playlist")
  private String thumbnail;

  @Schema(name = "description", description = "Description about Playlist")
  private String description;

  @Schema(name = "isPublic", description = "Playlist is visible to the world or not")
  private Boolean isPublic = true;

  @Schema(
      name = "isCollaborative",
      description = "Playlist could be collaborative by others or not")
  private Boolean isCollaborative = false;

  @Schema(name = "totalViews", description = "Total views of Playlist page")
  private Long totalViews = 0L;

  @Schema(name = "totalLikes", description = "Total likes of Playlist")
  private Long totalLikes = 0L;

  @Schema(name = "totalShares", description = "Total shares of Playlist")
  private Long totalShares = 0L;

  @Length(min = 16, max = 16, message = "OwnerId must be 16 characters")
  private String ownerId;

  @Schema(name = "items", description = "List of items in Playlist")
  private List<Item> items = new LinkedList<>();

  @Schema(name = "owner", description = "Owner of Playlist")
  private UserDto owner;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(
      value = {"addedById"},
      allowSetters = true)
  public static class Item {
    private String id;

    private String urn;

    private String addedById;

    private UserDto addedBy;

    private ZonedDateTime addedAt;

    private TrackDto track;
  }
}
