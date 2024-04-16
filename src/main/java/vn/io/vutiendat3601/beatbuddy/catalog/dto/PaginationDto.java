package vn.io.vutiendat3601.beatbuddy.catalog.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto<T> {
  private List<T> elements;

  private Integer page;

  private Integer size;

  private Integer numOfElements;

  private Long totalElements;

  private Long totalPages;

  public static <T> PaginationDto<T> of(
      List<T> elements, Integer page, Integer size, Long totalElements) {
    return new PaginationDto<>(
        elements,
        page,
        size,
        elements.size(),
        totalElements,
        (long) Math.ceil((double) totalElements / size));
  }
}
