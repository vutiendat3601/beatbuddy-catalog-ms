package vn.io.datvutech.beatbuddy.catalog.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "tracks")
@Entity
public class Track {
    @Id
    @GeneratedValue(generator = "pg-uuid")
    private UUID trackId;

    private String id;

    private String urn;

    private String name;

    private Boolean isPublic;

    private String description;

    private String releasedDate;

    private Long durationSec;

    private Boolean isPlayable;

    private String thumbnail;
}
