package vn.io.vutiendat3601.beatbuddy.catalog.service;

import java.net.URI;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.CreatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistItemDto;

public interface PlaylistService {
  Mono<URI> createPlaylist(CreatePlaylistDto createPlaylistDto);

  Mono<Void> updatePlaylist(String id, UpdatePlaylistDto updatePlaylistDto);

  Mono<Void> addPlaylistItems(String id, UpdatePlaylistItemDto updateTrackItemDto);

  Mono<Void> removePlaylistItems(String id, UpdatePlaylistItemDto updateTrackItemDto);

  Mono<PlaylistDto> getPlaylist(String id);
}
