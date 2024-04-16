package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(
    value = {"authUserId"},
    allowSetters = true)
@Schema(name = "User", description = "Schema to hold User information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  @Schema(name = "id", description = "Unique identity of User")
  private String id;

  @Schema(name = "urn", description = "Unique identity for resources in Beat Buddy")
  private String urn;

  @Schema(name = "displayName", description = "The name displayed on the user profile")
  private String displayName;

  @Schema(name = "thumbnail", description = "The thumbnail image on the user profile")
  private String thumbnail;

  @Schema(name = "isPublic", description = "The user profile is visible to the world or not")
  private Boolean isPublic;

  @Schema(name = "description", description = "The biography on the user profile")
  private String description;

  @Schema(name = "authUserId", description = "The User ID in the authentication system")
  private String authUserId;
}
