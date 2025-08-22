package com.rafaelw.financeControl.infra.persist.repository.specifications;

import com.rafaelw.financeControl.infra.persist.entities.DebitPersist;
import java.math.BigDecimal;
import java.time.Instant;
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

  public static Specification<DebitPersist> findNextDebitMoment(Instant moment) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("moment"), moment);
  }

  public static Specification<DebitPersist> findPreviousDebitMoment(Instant moment) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get("moment"), moment);
  }

  public static Specification<DebitPersist> findGreaterThanDebitId(Long id) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("id"), id);
  }

  public static Specification<DebitPersist> findLessThanDebitId(Long id) {
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
