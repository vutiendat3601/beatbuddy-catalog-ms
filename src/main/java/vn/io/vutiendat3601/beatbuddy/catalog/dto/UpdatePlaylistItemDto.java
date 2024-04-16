package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdatePlaylistItemDto {
  @Schema(name = "position", description = "Position of track items in playlist")
  @NotNull(groups = AddItem.class)
  @PositiveOrZero(groups = AddItem.class)
  private Integer position = 0;

  @Schema(name = "addedById", description = "who add track items to playlist")
  private String addedById;

  @Schema(name = "itemUrns", description = "List of item urn to remove from playlist")
  @NotEmpty(message = "itemUrns must not be null or empty", groups = AddItem.class)
  @Size(
      message = "itemUrns size must be in range [1,50]",
      min = 1,
      max = 50,
      groups = AddItem.class)
  private List<
          @NotNull(message = "itemUrns must have valid urns", groups = AddItem.class)
          @Pattern(
              message = "itemUrns must have valid urns",
              regexp = "^beatbuddy:track:[a-zA-Z0-9]{16}$",
              groups = AddItem.class)
          String>
      itemUrns = new LinkedList<>();

  @Schema(name = "itemIds", description = "List of item ids to remove from playlist")
  @NotEmpty(message = "itemIds must not be null or empty", groups = RemoveItem.class)
  private List<
          @NotEmpty(message = "itemIds must have valid ids", groups = RemoveItem.class)
          @Length(
              message = "itemIds must have valid ids [16 characters]",
              min = 16,
              max = 16,
              groups = RemoveItem.class)
          String>
      itemIds = new LinkedList<>();

  public static interface AddItem {}

  public static interface RemoveItem {}
}
