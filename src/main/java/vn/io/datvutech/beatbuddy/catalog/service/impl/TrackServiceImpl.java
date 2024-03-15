package vn.io.datvutech.beatbuddy.catalog.service.impl;

import static vn.io.datvutech.beatbuddy.catalog.constant.TrackConstant.TRACK;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.io.datvutech.beatbuddy.catalog.dto.ArtistDto;
import vn.io.datvutech.beatbuddy.catalog.dto.TrackDto;
import vn.io.datvutech.beatbuddy.catalog.entity.Track;
import vn.io.datvutech.beatbuddy.catalog.exception.ResourceNotFoundException;
import vn.io.datvutech.beatbuddy.catalog.mapper.ArtistMapper;
import vn.io.datvutech.beatbuddy.catalog.mapper.TrackMapper;
import vn.io.datvutech.beatbuddy.catalog.repository.ArtistRepository;
import vn.io.datvutech.beatbuddy.catalog.repository.TrackArtistRepository;
import vn.io.datvutech.beatbuddy.catalog.repository.TrackRepository;
import vn.io.datvutech.beatbuddy.catalog.service.TrackService;

@RequiredArgsConstructor
@Service
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepo;
    private final ArtistRepository artistRepo;
    private final TrackArtistRepository trackArtistRepo;

    @Override
    public TrackDto getTrack(String id) {
        Track track = trackRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TRACK, "id", id));
        List<UUID> artistIds = trackArtistRepo
                .findAllByTrackId(track.getTrackId())
                .stream().map(ta -> ta.getArtistId()).toList();
        List<ArtistDto> artistDtos = artistRepo.findAllByArtistIdIn(artistIds).stream()
                .map(artist -> ArtistMapper.mapToArtistDto(artist, new ArtistDto())).toList();

        TrackDto trackDto = TrackMapper.mapToTrackDto(track, new TrackDto());
        trackDto.setArtists(artistDtos);
        return trackDto;
    }
}
