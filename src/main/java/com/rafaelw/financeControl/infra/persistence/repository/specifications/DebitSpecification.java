package com.rafaelw.financeControl.infra.persistence.repository.specifications;

import com.rafaelw.financeControl.application.dto.debit.DebitFilterDTO;
import com.rafaelw.financeControl.infra.persistence.entities.DebitPersist;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class DebitSpecification {

  public static  Specification<DebitPersist> addFilter(DebitFilterDTO filter){
    return ((root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.categoryId() != null){
        predicates.add(cb.equal(root.get("category").get("id"), filter.categoryId()));
      }

      if(filter.categoryName() != null){
        predicates.add(cb.equal(root.get("category").get("name"), filter.categoryName()));
      }

      if(filter.minAmount() != null){
        predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filter.minAmount()));
      }

      if(filter.maxAmount() != null){
        predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filter.maxAmount()));
      }

      if (filter.since() != null ){
        LocalDate localDate = LocalDate.parse(filter.since());
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneOffset.UTC);
        Instant moment = zonedDateTime.toInstant();
        predicates.add(cb.greaterThanOrEqualTo(root.get("moment"), moment));
      }

      if(filter.until() != null){
        LocalDate localDate = LocalDate.parse(filter.until());
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneOffset.UTC);
        Instant moment = zonedDateTime.toInstant();
        predicates.add(cb.lessThanOrEqualTo(root.get("moment"), moment));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    });
  }

}
