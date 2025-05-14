package com.crud.crud.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponses<T> {
  private List<T> contents;
  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;
}
