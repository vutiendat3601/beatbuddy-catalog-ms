package vn.io.datvutech.beatbuddy.catalog.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.io.datvutech.beatbuddy.catalog.dto.ArtistDto;
import vn.io.datvutech.beatbuddy.catalog.service.ArtistService;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/artists")
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping("{id}")
    public ResponseEntity<ArtistDto> getArtist(
            @Length(min = 16, max = 16, message = "Artist ID must be 16 characters") @PathVariable String id) {
        ArtistDto artistDto = artistService.getArtist(id);
        return ResponseEntity.ok(artistDto);
    }
}
