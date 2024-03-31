package vn.io.vutiendat3601.beatbuddy.catalog.constant;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;

public interface TrackConstant {
    int ID_LENGTH = 16;
    String TRACK = "Track";
    static ParameterizedTypeReference<List<TrackDto>> TRACK_DTO_LIST_REF = new ParameterizedTypeReference<>() {
    };
}
