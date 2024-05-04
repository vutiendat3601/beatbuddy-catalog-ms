package vn.io.vutiendat3601.beatbuddy.catalog.constant;

public interface TrackSuggestionConstant {
  int TRACK_SUGGESTION_ID_LENGTH = 16;
  int TRACK_SUGGESTION_ITEM_ID_LENGTH = 16;
  String TRACK_SUGGESTION = "Track Suggestion";
  String TRACK_SUGGESTION_URN_PREFIX = "beatbuddy:track-suggestion";
  String TRACK_SUGGESTION_URI_PREFIX = "/v1/catalog/track-suggestions";

  String STATUS_200 = "200";
  String MESSAGE_200 = "Request processed successfully";

  String STATUS_201 = "201";
  String MESSAGE_201 = "Created track suggestion successfully";

  String TRACK_SUGGESTION_VIEW_SCOPE = "track-suggestion:view";
  String TRACK_SUGGESTION_CREATE_SCOPE = "track-suggestion:create";
  String TRACK_SUGGESTION_UPDATE_SCOPE = "track-suggestion:edit";
  String TRACK_SUGGESTION_DELETE_SCOPE = "track-suggestion:delete";

  enum Status {
    PENDING,
    PARSED_METADATA,
    APPROVED,
    REJECTED
  }
}
