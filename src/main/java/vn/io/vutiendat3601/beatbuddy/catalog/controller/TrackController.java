package vn.io.vutiendat3601.beatbuddy.catalog.controller;

import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackConstant.ID_LENGTH;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;
import vn.io.vutiendat3601.beatbuddy.catalog.service.TrackService;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/catalog/tracks")
public class TrackController {
  private final TrackService trackService;

  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<TrackDto>> getTrack(
      @Length(
              min = ID_LENGTH,
              max = ID_LENGTH,
              message = "Track ID must be " + ID_LENGTH + " characters")
          @PathVariable
          String id) {
    return trackService.getTrack(id).map(trackDto -> ResponseEntity.ok(trackDto));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<List<TrackDto>>> getSeveralTracks(
      @Size(min = 1, max = 50, message = "ids size must be in range [1,50]")
          @NotEmpty(message = "ids must not be empty")
          @RequestParam
          List<String> ids) {
    return trackService.getSeveralTracks(ids).map(trackDtos -> ResponseEntity.ok(trackDtos));
  }
}
