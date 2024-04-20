package vn.io.vutiendat3601.beatbuddy.catalog.constant;

public interface PlaylistConstant {
  int PLAYLIST_ID_LENGTH = 16;
  String PLAYLIST = "Playlist";
  String PLAYLIST_URN_PREFIX = "beatbuddy:playlist";
  String PLAYLIST_PUBLIC_TYPE = "beatbuddy:playlist:public";
  String PLAYLIST_COLLABORATIVE_TYPE = "beatbuddy:playlist:collaborative";
  String PLAYLIST_URI_PREFIX = "/v1/catalog/playlists";

  // ## Scope
  String PLAYLIST_CREATE_SCOPE = "playlist:create";
  String PLAYLIST_VIEW_SCOPE = "playlist:view";
  String PLAYLIST_EDIT_SCOPE = "playlist:edit";
  String PLAYLIST_DELETE_SCOPE = "playlist:delete";
  String PLAYLIST_ADD_ITEM_SCOPE = "playlist:add-item";
  String PLAYLIST_REMOVE_ITEM_SCOPE = "playlist:remove-item";

  String STATUS_200 = "200";
  String MESSAGE_200 = "Request processed successfully";

  String STATUS_201 = "201";
  String MESSAGE_201 = "Created playlist successfully";
}
