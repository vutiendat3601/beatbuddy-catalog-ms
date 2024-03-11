package vn.io.datvutech.beatbuddy.catalog.service;

import org.hibernate.validator.constraints.Length;

import vn.io.datvutech.beatbuddy.catalog.dto.ArtistDto;

public interface ArtistService {
    ArtistDto getArtist(@Length(min = 16, max = 16, message = "Track ID must be 10 digits") String id);
}
