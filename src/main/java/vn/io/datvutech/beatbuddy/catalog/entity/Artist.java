package vn.io.datvutech.beatbuddy.catalog.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "artists")
@Entity
public class Artist extends AbstractCatalog {
    @Id
    @GeneratedValue(generator = "pg-uuid")
    private UUID artistId;

    private String id;

    private String urn;

    private String name;

    private Boolean isVerified;

    private Boolean isPublic;

    private String realName;

    private LocalDate birthDate;

    private String description;

    private String nationality;

    private String biography;

    private String thumbnail;

    private String background;

    private String refCode;
}
