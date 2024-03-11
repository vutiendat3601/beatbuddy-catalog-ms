package vn.io.datvutech.beatbuddy.catalog.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractCatalog extends AbstractEntity {
    private Long totalViews = 0L;

    private Long totalLikes = 0L;

    private Long totalShares = 0L;
}
