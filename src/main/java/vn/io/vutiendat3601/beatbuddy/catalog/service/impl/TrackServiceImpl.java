package vn.io.vutiendat3601.beatbuddy.catalog.service.impl;

import static vn.io.vutiendat3601.beatbuddy.catalog.constant.TrackConstant.TRACK;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;
import vn.io.vutiendat3601.beatbuddy.catalog.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.beatbuddy.catalog.service.TrackService;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.ArtistClient;
import vn.io.vutiendat3601.beatbuddy.catalog.service.client.TrackClient;

@RequiredArgsConstructor
@Service
public class TrackServiceImpl implements TrackService {
  private final TrackClient trackClient;
  private final ArtistClient artistClient;

  @Override
  public Mono<TrackDto> getTrack(String id) {
    return trackClient
        .getTrack(id)
        .map(ResponseEntity::getBody)
        .flatMap(
            trackDto ->
                artistClient
                    .getSeveralArtists(trackDto.getArtistIds())
                    .map(ResponseEntity::getBody)
                    .onErrorReturn(new LinkedList<>())
                    .map(
                        artistDtos -> {
                          trackDto.setArtists(artistDtos);
                          return trackDto;
                        }))
        .onErrorResume(
            WebClientResponseException.NotFound.class,
            e -> Mono.error(new ResourceNotFoundException(TRACK, "id", id)));
  }

  @Override
  public Mono<List<TrackDto>> getSeveralTracks(List<String> ids) {
    trackClient
        .getSeveralTracks(ids)
        .map(ResponseEntity::getBody)
        .flatMap(
            trackDtos -> {
              final Set<String> artistIds = new LinkedHashSet<>();
              trackDtos.forEach(trackDto -> artistIds.addAll(trackDto.getArtistIds()));
              Flux.fromIterable(artistIds)
                  .buffer(50)
                  .flatMap(
                      artistIdsTrunk -> {
                        return artistClient
                            .getSeveralArtists(artistIdsTrunk)
                            .map(ResponseEntity::getBody);
                      });
              return artistClient.getSeveralArtists(ids).map(ResponseEntity::getBody);
            });
    return trackClient
        .getSeveralTracks(ids)
        .map(ResponseEntity::getBody)
        .flatMapMany(Flux::fromIterable)
        .flatMap(
            trackDto ->
                artistClient
                    .getSeveralArtists(trackDto.getArtistIds())
                    .map(ResponseEntity::getBody)
                    .onErrorReturn(new LinkedList<>())
                    .map(
                        artistDtos -> {
                          trackDto.setArtists(artistDtos);
                          return trackDto;
                        }))
        .collectList();
  }
}
