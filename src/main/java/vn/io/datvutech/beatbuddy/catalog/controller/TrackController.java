package vn.io.datvutech.beatbuddy.catalog.controller;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import vn.io.datvutech.beatbuddy.catalog.dto.TrackDto;
import vn.io.datvutech.beatbuddy.catalog.service.TrackService;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/tracks")
public class TrackController {
    private final TrackService trackService;

    @GetMapping("{id}")
    public ResponseEntity<TrackDto> getTrack(
            @Length(min = 16, max = 16, message = "Track ID must be 16 characters") 
            @PathVariable String id
            
        ) {
        TrackDto trackDto = trackService.getTrack(id);
        return ResponseEntity.ok(trackDto);
    }

    @GetMapping
    public ResponseEntity<List<TrackDto>> getSeveralTracks(
            @Size(min = 1, max = 50, message = "ids size must be between 1 and 50") 
            @NotEmpty(message = "ids must not be empty") 
            @RequestParam List<String> ids

    ) {
        List<TrackDto> trackDtos = trackService.getSeveralTracks(ids);
        return ResponseEntity.ok(trackDtos);
    }
}
