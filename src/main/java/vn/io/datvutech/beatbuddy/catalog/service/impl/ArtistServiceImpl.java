package vn.io.datvutech.beatbuddy.catalog.service.impl;

import static vn.io.datvutech.beatbuddy.catalog.constant.ArtistConstant.ARTIST;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.io.datvutech.beatbuddy.catalog.dto.ArtistDto;
import vn.io.datvutech.beatbuddy.catalog.entity.Artist;
import vn.io.datvutech.beatbuddy.catalog.exception.ResourceNotFoundException;
import vn.io.datvutech.beatbuddy.catalog.mapper.ArtistMapper;
import vn.io.datvutech.beatbuddy.catalog.repository.ArtistRepository;
import vn.io.datvutech.beatbuddy.catalog.service.ArtistService;

@RequiredArgsConstructor
@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepo;

    @Override
    public ArtistDto getArtist(@Length(min = 16, max = 16, message = "Track ID must be 10 digits") String id) {
        Artist track = artistRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ARTIST, "id", id));
        ArtistDto artistDto = ArtistMapper.mapToArtistDto(track, new ArtistDto());
        return artistDto;
    }
}
