package vn.io.vutiendat3601.beatbuddy.catalog.service.impl;

import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackConstant.TRACK;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDetailDto;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.catalog.mapper.TrackMapper;
import vn.io.vutiendat3601.beatbuddy.catalog.service.TrackService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.ArtistClient;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.TrackClient;

@RequiredArgsConstructor
@Service
public class TrackServiceImpl implements TrackService {
    private final TrackClient trackClient;
    private final ArtistClient artistClient;

    @Override
    public Mono<TrackDetailDto> getTrackDetailById(String id) {
        return trackClient
                .getTrackById(id)
                .map(ResponseEntity::getBody)
                .flatMap(trackDto -> artistClient
                        .getSeveralArtists(trackDto.getArtistIds())
                        .map(ResponseEntity::getBody)
                        .onErrorReturn(new LinkedList<>())
                        .map(artistDtos -> {
                            TrackDetailDto trackDetailDto = TrackMapper.mapToTrackDetail(trackDto);
                            trackDetailDto.setArtists(artistDtos);
                            return trackDetailDto;
                        }))
                .onErrorResume(
                        WebClientResponseException.NotFound.class,
                        e -> Mono.error(new ResourceNotFoundException(TRACK, "id", id))

                );
    }

    @Override
    public Mono<List<TrackDetailDto>> getSeveralTrackDetails(List<String> ids) {
        return trackClient
                .getSeveralTracks(ids)
                .map(ResponseEntity::getBody)
                .flatMapMany(Flux::fromIterable)
                .flatMap(trackDto -> artistClient
                        .getSeveralArtists(trackDto.getArtistIds())
                        .map(ResponseEntity::getBody)
                        .onErrorReturn(new LinkedList<>())
                        .map(artistDtos -> {
                            TrackDetailDto trackDetailDto = TrackMapper.mapToTrackDetail(trackDto);
                            trackDetailDto.setArtists(artistDtos);
                            return trackDetailDto;
                        }))
                .collectList();
    }
}
