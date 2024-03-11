package vn.io.datvutech.beatbuddy.catalog.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.io.datvutech.beatbuddy.catalog.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    Optional<Artist> findById(String id);

    List<Artist> findAllByArtistIdIn(Iterable<UUID> artistIds);
}
