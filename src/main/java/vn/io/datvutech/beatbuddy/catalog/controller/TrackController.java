package vn.io.datvutech.beatbuddy.catalog.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.io.datvutech.beatbuddy.catalog.dto.TrackDto;
import vn.io.datvutech.beatbuddy.catalog.service.TrackService;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/tracks")
public class TrackController {
    private final TrackService trackService;

    @GetMapping("{id}")
    public ResponseEntity<TrackDto> getTrack(@Length(min = 16, max = 16, message = "Track ID must be 10 digits") @PathVariable String id) {
        TrackDto trackDto = trackService.getTrack(id);
        return ResponseEntity.ok(trackDto);
    }
}
