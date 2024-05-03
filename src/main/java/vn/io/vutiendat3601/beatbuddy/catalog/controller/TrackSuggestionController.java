package vn.io.vutiendat3601.beatbuddy.catalog.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.MESSAGE_201;
import static vn.io.vutiendat3601.beatbuddy.catalog.constant.PlaylistConstant.STATUS_201;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.CreateTrackSuggestionDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ResponseDto;
import vn.io.vutiendat3601.beatbuddy.catalog.service.TrackSuggestionService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/catalog/track-suggestions")
public class TrackSuggestionController {
  private final TrackSuggestionService trackSuggService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<ResponseDto>> createTrackSuggestion(
      @Valid @RequestBody CreateTrackSuggestionDto createTrackSuggDto) {
    log.info("Create TrackSuggestion");
    log.debug("Create TrackSuggestion: createTrackSuggestion={}", createTrackSuggDto);
    return trackSuggService
        .createTrackSuggestion(createTrackSuggDto)
        .map(ResponseEntity::created)
        .map(respEntity -> respEntity.body(new ResponseDto(STATUS_201, MESSAGE_201)));
  }
}
