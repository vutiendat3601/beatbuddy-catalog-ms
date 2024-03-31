package vn.io.vutiendat3601.beatbuddy.catalog.mapper;

import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDetailDto;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.TrackDto;

public interface TrackMapper {

    static TrackDetailDto mapToTrackDetail(TrackDto trackDto) {
        return mapToTrackDetail(trackDto, new TrackDetailDto());
    }

    static TrackDetailDto mapToTrackDetail(TrackDto trackDto, TrackDetailDto trackDetailDto) {
        trackDetailDto.setId(trackDto.getId());
        trackDetailDto.setUrn(trackDto.getUrn());
        trackDetailDto.setName(trackDto.getName());
        trackDetailDto.setDurationSec(trackDto.getDurationSec());
        trackDetailDto.setDescription(trackDto.getDescription());
        trackDetailDto.setReleasedDate(trackDto.getReleasedDate());
        trackDetailDto.setThumbnail(trackDto.getThumbnail());
        trackDetailDto.setIsPublic(trackDto.getIsPublic());
        trackDetailDto.setIsPlayable(trackDto.getIsPlayable());
        trackDetailDto.setTotalViews(trackDto.getTotalViews());
        trackDetailDto.setTotalLikes(trackDto.getTotalLikes());
        trackDetailDto.setTotalShares(trackDto.getTotalShares());
        trackDetailDto.setTotalListens(trackDto.getTotalListens());
        return trackDetailDto;
    }
}
