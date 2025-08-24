package com.rafaelw.financeControl.application.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class Pagination {

  public static <T, R> PaginatedResponse<R> paginate(JpaSpecificationExecutor<T> repository,
      Specification<T> spec, Integer pageSize, Long cursor, Function<T, Long> idExtractor,
      Function<T, R> mapper, String idProperty) {

    Long startCursor;
    Long endCursor;
    Long nextPage = null;
    Long previousPage = null;

    int queryPageSize = pageSize + 1;

    Sort sortAsc = Sort.by(idProperty).ascending();
    Sort sortDesc = Sort.by(idProperty).descending();
    Pageable pageAsc = PageRequest.of(0, queryPageSize, sortAsc);
    Pageable pageDesc = PageRequest.of(0, queryPageSize, sortDesc);

    Specification<T> finalSpec = Specification.allOf(spec);

    if (cursor != null) {
      finalSpec = finalSpec.and(
          (root, query, cb) -> cb.lessThanOrEqualTo(root.get(idProperty), cursor));
    }

    Page<T> currentPage = repository.findAll(finalSpec, pageDesc);
    List<T> content = new ArrayList<>(currentPage.getContent());

    if (content.size() > pageSize) {
      nextPage = idExtractor.apply(content.getLast());
      content.removeLast();
    }

    if (cursor != null) {
      Specification<T> previousPageSpec = Specification.allOf(spec)
          .and((root, query, cb) -> cb.greaterThanOrEqualTo(
              root.get(idProperty), cursor));
      Page<T> previousPageCheck = repository.findAll(previousPageSpec, pageAsc);
      List<T> previousPageCheckList = previousPageCheck.getContent();

      if (previousPageCheckList.size() > pageSize) {
        previousPage = idExtractor.apply(
            previousPageCheck.getContent().getLast());
      }
    }

    if (content.isEmpty()) {
      return new PaginatedResponse<>(List.of(), null, null, null, null);
    }

    List<R> responseDTOs = content.stream().map(mapper).sorted(
            Comparator.comparing(r -> idExtractor.apply(content.getFirst())))
        .toList();

    startCursor = idExtractor.apply(content.getFirst());
    endCursor = idExtractor.apply(content.getLast());

    return new PaginatedResponse<>(responseDTOs, startCursor, endCursor, nextPage, previousPage);
  }
}
