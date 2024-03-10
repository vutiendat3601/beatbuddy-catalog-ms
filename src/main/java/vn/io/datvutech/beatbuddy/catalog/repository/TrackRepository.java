package vn.io.datvutech.beatbuddy.catalog.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.io.datvutech.beatbuddy.catalog.entity.Track;

public interface TrackRepository extends JpaRepository<Track, UUID> {
    Optional<Track> findById(String id);
}
