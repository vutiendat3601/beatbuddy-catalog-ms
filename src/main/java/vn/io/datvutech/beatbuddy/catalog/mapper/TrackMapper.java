package vn.io.datvutech.beatbuddy.catalog.mapper;

import vn.io.datvutech.beatbuddy.catalog.dto.TrackDto;
import vn.io.datvutech.beatbuddy.catalog.entity.Track;

public class TrackMapper {
    public static TrackDto mapToTrackDto(Track track, TrackDto trackDto) {
        trackDto.setId(track.getId());
        trackDto.setUrn(track.getUrn());
        trackDto.setName(track.getName());
        trackDto.setDurationSec(track.getDurationSec());
        trackDto.setDescription(track.getDescription());
        trackDto.setReleasedDate(track.getReleasedDate());
        trackDto.setThumbnail(track.getThumbnail());
        trackDto.setIsPublic(track.getIsPublic());
        trackDto.setIsPlayable(track.getIsPlayable());
        trackDto.setTotalViews(track.getTotalViews());
        trackDto.setTotalLikes(track.getTotalLikes());
        trackDto.setTotalShares(track.getTotalShares());
        trackDto.setTotalListens(track.getTotalListens());
        return trackDto;
    }
}
