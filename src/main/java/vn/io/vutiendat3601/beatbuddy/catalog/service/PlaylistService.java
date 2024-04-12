package vn.io.vutiendat3601.beatbuddy.catalog.service;

import java.net.URI;

import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PaginationDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistEditTrackItemDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistSnapshotDto;

public interface PlaylistService {
    Mono<URI> createPlaylist(PlaylistDto playlistDto);

    Mono<PaginationDto<PlaylistSnapshotDto>> getPlaylistSnapshots(String id, int page, int size);

    Mono<Void> updatePlaylist(String id, PlaylistDto playlistDto);

    Mono<Void> addPlaylistTrackItems(String id, PlaylistEditTrackItemDto editTrackItemDto);

    Mono<Void> removePlaylistTrackItems(String id, PlaylistEditTrackItemDto editTrackItemDto);

    Mono<PlaylistDto> getPlaylist(String id);
}
