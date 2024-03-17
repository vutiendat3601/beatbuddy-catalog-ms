package vn.io.datvutech.beatbuddy.catalog.service;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import vn.io.datvutech.beatbuddy.catalog.dto.TrackDto;

public interface TrackService {
    TrackDto getTrack(String id);

    List<TrackDto> getSeveralTracks(@NotEmpty @Size(min = 1, max = 50) List<String> ids);
}
