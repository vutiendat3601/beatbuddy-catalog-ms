package vn.io.vutiendat3601.beatbuddy.catalog.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.MESSAGE_200;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.MESSAGE_201;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.PLAYLIST_ID_LENGTH;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.STATUS_200;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.STATUS_201;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PaginationDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto.UpdatePlaylist;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistEditTrackItemDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistEditTrackItemDto.AddTrackItem;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistEditTrackItemDto.RemoveTrackItem;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistSnapshotDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ResponseDto;
import vn.io.vutiendat3601.beatbuddy.catalog.service.PlaylistService;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/catalog/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseDto>> createPlaylist(@Valid @RequestBody PlaylistDto playlistDto) {
        return playlistService.createPlaylist(playlistDto)
                .map(location -> ResponseEntity
                        .created(location)
                        .body(new ResponseDto(STATUS_201, MESSAGE_201)));
    }

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseDto>> updatePlaylist(
            @Length(
                min = PLAYLIST_ID_LENGTH, max = PLAYLIST_ID_LENGTH,
                message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters"
            )
            @PathVariable String id,
            @Validated(UpdatePlaylist.class) @RequestBody PlaylistDto playlistDto

    ) {
        return playlistService.updatePlaylist(id, playlistDto)
                .then(Mono.just(ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200))));
    }

    @GetMapping(path = "{id}")
    public Mono<ResponseEntity<PlaylistDto>> getPlaylist(
            @Length(
                min = PLAYLIST_ID_LENGTH, max = PLAYLIST_ID_LENGTH,
                message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters"
            )
            @PathVariable String id

    ) {
        return playlistService.getPlaylist(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping(path = "{id}/track-items", consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseDto>> addPlaylistTrackItems(
            @Length(
                min = PLAYLIST_ID_LENGTH, max = PLAYLIST_ID_LENGTH,
                message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters"
            )
            @PathVariable String id,
            @Validated(AddTrackItem.class)
            @RequestBody PlaylistEditTrackItemDto editTrackItemDto

    ) {
        return playlistService.addPlaylistTrackItems(id, editTrackItemDto)
                .then(Mono.just(ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200))));
    }

    @DeleteMapping(path = "{id}/track-items")
    public Mono<ResponseEntity<ResponseDto>> removePlaylistTrackItems(
            @Length(
                min = PLAYLIST_ID_LENGTH, max = PLAYLIST_ID_LENGTH,
                message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters"
            )
            @PathVariable String id,
            @Validated(RemoveTrackItem.class)
            @RequestBody PlaylistEditTrackItemDto editTrackItemDto

    ) {
        return playlistService.removePlaylistTrackItems(id, editTrackItemDto)
                .then(Mono.just(ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200))));
    }

    @GetMapping(path = "{id}/snapshots")
    public Mono<ResponseEntity<PaginationDto<PlaylistSnapshotDto>>> getPlaylistSnapshots(
            @Length(
                min = PLAYLIST_ID_LENGTH, max = PLAYLIST_ID_LENGTH,
                message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters"
            )
            @PathVariable String id,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size

    ) {
        return playlistService.getPlaylistSnapshots(id, page, size)
                .map(snapshotDtosPage -> ResponseEntity.ok(snapshotDtosPage));
    }
}
