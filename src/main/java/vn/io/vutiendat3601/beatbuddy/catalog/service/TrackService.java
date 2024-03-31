package vn.io.vutiendat3601.beatbuddy.catalog.service;

import java.util.List;

import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDetailDto;

public interface TrackService {
    Mono<TrackDetailDto> getTrackDetailById(String id);

    Mono<List<TrackDetailDto>> getSeveralTrackDetails(List<String> ids);
}
