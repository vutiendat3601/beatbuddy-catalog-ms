package vn.io.vutiendat3601.beatbuddy.catalog.constant;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

import vn.io.vutiendat3601.beatbuddy.catalog.dto.ArtistDto;

public interface ArtistConstant {
    int ID_LENGTH = 16;
    String ARTIST = "Artist";

    static ParameterizedTypeReference<List<ArtistDto>> ARTIST_DTO_LIST_REF = new ParameterizedTypeReference<>() {
    };
}
