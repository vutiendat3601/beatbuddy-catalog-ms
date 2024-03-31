package vn.io.vutiendat3601.beatbuddy.catalog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/artists")
public class ArtistController {
    // private final ArtistService artistService;

    // @GetMapping("{id}")
    // public ResponseEntity<ArtistDto> getArtist(
    //         @Length(min = 16, max = 16, message = "Artist ID must be 16 characters") @PathVariable String id) {
    //     ArtistDto artistDto = artistService.getArtist(id);
    //     return ResponseEntity.ok(artistDto);
    // }
}
