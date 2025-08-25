package com.rafaelw.financeControl.application.utils;

import com.rafaelw.financeControl.infra.persist.entities.JpaEntity;
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

  public static <T extends JpaEntity, R> PaginatedResponse<R> paginate(
      JpaSpecificationExecutor<T> repository,
      Specification<T> spec, Integer pageSize, Long cursor,
      Function<T, R> mapper, String keyProperty) {

    Long startCursor;
    Long endCursor;
    Long nextPage = null;
    Long previousPage = null;

    if (pageSize == null) {
      pageSize = 10;
    }

    int queryPageSize = pageSize + 1;

    Sort sortAsc = Sort.by(keyProperty).ascending();
    Sort sortDesc = Sort.by(keyProperty).descending();
    Pageable pageAsc = PageRequest.of(0, queryPageSize, sortAsc);
    Pageable pageDesc = PageRequest.of(0, queryPageSize, sortDesc);

    Specification<T> finalSpec = Specification.allOf(spec);

    if (cursor != null) {
      finalSpec = finalSpec.and(
          (root, query, cb) -> cb.lessThanOrEqualTo(root.get(keyProperty), cursor));
    }

    Page<T> currentPage = repository.findAll(finalSpec, pageDesc);
    List<T> content = new ArrayList<>(currentPage.getContent());

    if (content.isEmpty()) {
      return new PaginatedResponse<>(List.of(), null, null, null, null);
    }

    if (content.size() > pageSize) {
      nextPage = content.getLast().getId();
      content.removeLast();
    }

    if (cursor != null) {
      Specification<T> previousPageSpec = Specification.allOf(spec)
          .and((root, query, cb) -> cb.greaterThanOrEqualTo(
              root.get(keyProperty), cursor));
      Page<T> previousPageCheck = repository.findAll(previousPageSpec, pageAsc);
      List<T> previousPageCheckList = previousPageCheck.getContent();

      if (previousPageCheckList.size() > pageSize) {
        previousPage = previousPageCheck.getContent().getLast().getId();
      }
    }

    List<R> response = content.stream().map(mapper).sorted(
            Comparator.comparing(r -> content.getFirst().getId()))
        .toList();

    startCursor = content.getFirst().getId();
    endCursor = content.getLast().getId();

    return new PaginatedResponse<>(response, startCursor, endCursor, nextPage, previousPage);
  }
}
