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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.CreatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.PlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ResponseDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistItemDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistItemDto.AddItem;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.UpdatePlaylistItemDto.RemoveItem;
import vn.io.vutiendat3601.beatbuddy.catalog.service.PlaylistService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/catalog/playlists")
public class PlaylistController {
  private final PlaylistService playlistService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<ResponseDto>> createPlaylist(
      @Valid @RequestBody CreatePlaylistDto createPlaylistDto) {
    log.info("Create Playlist");
    log.debug("Create Playlist: createPlaylistDto={}", createPlaylistDto);
    return playlistService
        .createPlaylist(createPlaylistDto)
        .map(
            location ->
                ResponseEntity.created(location).body(new ResponseDto(STATUS_201, MESSAGE_201)));
  }

  @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<ResponseDto>> updatePlaylist(
      @Length(
              min = PLAYLIST_ID_LENGTH,
              max = PLAYLIST_ID_LENGTH,
              message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters")
          @PathVariable
          String id,
      @Valid @RequestBody UpdatePlaylistDto updatePlaylistDto) {

    log.info("Update Playlist: id={}", id);
    log.debug("Update Playlist: updatePlaylistDto={}", updatePlaylistDto);
    return playlistService
        .updatePlaylist(id, updatePlaylistDto)
        .then(Mono.just(ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200))));
  }

  // @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<ResponseDto>> createCollaborationInvitation(
      @Length(
              min = PLAYLIST_ID_LENGTH,
              max = PLAYLIST_ID_LENGTH,
              message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters")
          @PathVariable
          String id) {
    // log.info("Update Playlist: id={}", id);
    // log.debug("Update Playlist: updatePlaylistDto={}", updatePlaylistDto);
    // return playlistService
    //     .updatePlaylist(id, updatePlaylistDto)
    //     .then(Mono.just(ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200))));
    return Mono.empty();
  }

  @GetMapping(path = "{id}")
  public Mono<ResponseEntity<PlaylistDto>> getPlaylist(
      @Length(
              min = PLAYLIST_ID_LENGTH,
              max = PLAYLIST_ID_LENGTH,
              message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters")
          @PathVariable
          String id) {
    log.info("Get Playlist: id={}", id);
    return playlistService.getPlaylist(id).map(ResponseEntity::ok);
  }

  @PutMapping(path = "{id}/items", consumes = APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<ResponseDto>> addPlaylistItems(
      @Length(
              min = PLAYLIST_ID_LENGTH,
              max = PLAYLIST_ID_LENGTH,
              message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters")
          @PathVariable
          String id,
      @Validated(AddItem.class) @RequestBody UpdatePlaylistItemDto addPlaylistItemDto) {
    log.info("Add Playlist Item: id={}", id);
    log.debug("Add Playlist Item: addPlaylistItemDto={}", addPlaylistItemDto);
    return playlistService
        .addPlaylistItems(id, addPlaylistItemDto)
        .then(Mono.just(ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200))));
  }

  @DeleteMapping(path = "{id}/items")
  public Mono<ResponseEntity<ResponseDto>> removePlaylistItems(
      @Length(
              min = PLAYLIST_ID_LENGTH,
              max = PLAYLIST_ID_LENGTH,
              message = "Playlist ID must be " + PLAYLIST_ID_LENGTH + " characters")
          @PathVariable
          String id,
      @Validated(RemoveItem.class) @RequestBody UpdatePlaylistItemDto removePlaylistItemDto) {
    log.info("Remove Playlist Item: id={}", id);
    log.debug("Remove Playlist Item: removePlaylistItemDto={}", removePlaylistItemDto);
    return playlistService
        .removePlaylistItems(id, removePlaylistItemDto)
        .then(Mono.just(ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200))));
  }
}
