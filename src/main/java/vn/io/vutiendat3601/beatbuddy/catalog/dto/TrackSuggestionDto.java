package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackSuggestionConstant.Status;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrackSuggestionDto {
  private String id;

  private String urn;

  private String name;

  private String url;

  private String originalUrl;

  private String description;

  private String thumbnail;

  private String releasedDate;

  private Integer durationSec;

  private Status status = Status.PENDING;

  private String trackId;

  private Boolean isPlayable = false;

  private String fileId;

  private List<String> artistIds = new LinkedList<>();
  
  private String ownerId;
}
