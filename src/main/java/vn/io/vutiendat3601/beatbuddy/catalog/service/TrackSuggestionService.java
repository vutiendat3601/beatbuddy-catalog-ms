package vn.io.vutiendat3601.beatbuddy.catalog.service;

import java.net.URI;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.CreateTrackSuggestionDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackSuggestionDto;

public interface TrackSuggestionService {
  Mono<URI> createTrackSuggestion(CreateTrackSuggestionDto createTrackSuggDto);

  Mono<TrackSuggestionDto> getTrackSuggestion(String id);
}
