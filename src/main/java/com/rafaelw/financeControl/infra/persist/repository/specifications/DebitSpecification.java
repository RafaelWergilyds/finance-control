package com.rafaelw.financeControl.infra.persist.repository.specifications;

import com.rafaelw.financeControl.infra.persist.entities.DebitPersist;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

public class DebitSpecification {

  public static Specification<DebitPersist> findByUserId(Long id) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("user").get("id"), id);
  }

  public static Specification<DebitPersist> findByCategoryId(Long id) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("category").get("id"), id);
  }

  public static Specification<DebitPersist> findByCategoryName(String name) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("category").get("name"), name);
  }

  public static Specification<DebitPersist> findNextDebitId(Long id) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("id"), id);
  }

  public static Specification<DebitPersist> findPreviousDebitId(Long id) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get("id"), id);
  }

  public static Specification<DebitPersist> hasAmountLessThanEqualsTo(BigDecimal amount) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(
            root.get("amount"), amount);
  }

  public static Specification<DebitPersist> hasAmountGreaterThanOrEqualsTo(BigDecimal amount) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(
            root.get("amount"), amount);
  }

}
