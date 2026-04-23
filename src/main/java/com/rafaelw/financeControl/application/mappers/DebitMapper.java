package com.rafaelw.financeControl.application.mappers;

import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.domain.model.entities.Category;
import com.rafaelw.financeControl.domain.model.entities.Debit;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.DebitPersist;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.UserPersist;
import org.springframework.stereotype.Component;

@Component
public class DebitMapper {

  public DebitResponseDTO toResponse(DebitPersist debitPersist) {
    Long categoryId = debitPersist.getCategory() != null ? debitPersist.getCategory().getId() : null;

    return new DebitResponseDTO(debitPersist.getId(), debitPersist.getName(), debitPersist.getAmount(), debitPersist.getMoment(), categoryId);
  }

  public Debit toDomain(DebitPersist debitPersist) {
    Debit debit = new Debit();

    debit.setId(debitPersist.getId());
    debit.setName(debitPersist.getName());
    debit.setAmount(debitPersist.getAmount());
    debit.setMoment(debitPersist.getMoment());
    debit.setUserId(debitPersist.getUser().getId());

    if(debitPersist.getCategory() != null){
      Category category = new Category();
      category.setId(debitPersist.getCategory().getId());
      category.setName(debitPersist.getCategory().getName());
      category.setUserId(debit.getUserId());
    }

    return debit;
  }

  public DebitPersist toPersist(Debit debit) {
    DebitPersist debitPersist = new DebitPersist();

    debitPersist.setId(debit.getId());
    debitPersist.setName(debit.getName());
    debitPersist.setAmount(debit.getAmount());
    debitPersist.setMoment(debit.getMoment());

    if(debit.getUserId() != null){
      UserPersist userPersist = new UserPersist();
      userPersist.setId(debit.getUserId());
      debitPersist.setUser(userPersist);
    }

    if(debit.getCategoryId() != null){
      CategoryPersist categoryPersist = new CategoryPersist();
      categoryPersist.setId(debit.getCategoryId());
      debitPersist.setCategory(categoryPersist);
    }

    return debitPersist;
  }

}
