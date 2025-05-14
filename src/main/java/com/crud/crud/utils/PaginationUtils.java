package com.crud.crud.utils;

import com.crud.crud.dto.PaginationResponses;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

  public static <T, D> PaginationResponses<D> buildPaginationResponse(
      Page<T> page, List<D> content) {
    final PaginationResponses<D> response = new PaginationResponses<>();
    response.setContents(content);
    response.setPageNumber(page.getNumber() + 1);
    response.setPageSize(page.getSize());
    response.setTotalElements(page.getTotalElements());
    response.setTotalPages(page.getTotalPages());
    response.setLast(page.isLast());
    return response;
  }
}
