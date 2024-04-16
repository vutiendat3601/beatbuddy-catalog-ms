package vn.io.vutiendat3601.beatbuddy.catalog.service;

import java.util.List;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;

public interface TrackService {
  Mono<TrackDto> getTrack(String id);

  Mono<List<TrackDto>> getSeveralTracks(List<String> ids);
}
