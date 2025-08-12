package com.rafaelw.financeControl.infra.persist.repository.specifications;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {

  public static <T> Specification<T> empty() {
    return ((root, query, criteriaBuilder) -> null);
  }

}
