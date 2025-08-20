package com.rafaelw.financeControl.application.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination {

  public static Pageable paginate(int pageNumber, int pageSize) {
    Sort sortAsc = Sort.by("id").ascending();
    Sort sortDesc = Sort.by("id").descending();

    return PageRequest.of(pageNumber, pageSize, sortAsc);
  }

}
