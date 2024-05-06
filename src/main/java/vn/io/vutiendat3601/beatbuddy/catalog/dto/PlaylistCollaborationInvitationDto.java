package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import lombok.Data;

@Data
public class PlaylistCollaborationInvitationDto {
  private String playlistId;

  private String collaboratorId;

  public PlaylistCollaborationInvitationDto() {}
}
