package vn.io.datvutech.beatbuddy.catalog.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.io.datvutech.beatbuddy.catalog.entity.TrackArtist;

public interface TrackArtistRepository extends JpaRepository<TrackArtist, UUID> {
    List<TrackArtist> findAllByTrackId(UUID trackId);
}
