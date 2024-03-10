package vn.io.datvutech.beatbuddy.catalog.mapper;

import vn.io.datvutech.beatbuddy.catalog.dto.ArtistDto;
import vn.io.datvutech.beatbuddy.catalog.entity.Artist;

public class ArtistMapper {
    public static ArtistDto mapToArtistDto(Artist artist, ArtistDto artistDto) {
        artistDto.setId(artist.getId());
        artistDto.setUrn(artist.getUrn());
        artistDto.setName(artist.getName());
        artistDto.setIsVerified(artist.getIsVerified());
        artistDto.setPublic(artist.getIsPublic());
        artistDto.setRealName(artist.getRealName());
        artistDto.setBirthDate(artist.getBirthDate());
        artistDto.setDescription(artist.getDescription());
        artistDto.setNationality(artist.getNationality());
        artistDto.setBiography(artist.getBiography());
        artistDto.setThumbnail(artist.getThumbnail());
        artistDto.setBackground(artist.getBackground());
        return artistDto;
    }
}
