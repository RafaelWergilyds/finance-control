package com.rafaelw.financeControl.application.mappers;

import com.rafaelw.financeControl.application.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.application.mappers.struct.AvoidContext;
import com.rafaelw.financeControl.application.mappers.struct.DebitMapperStruct;
import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.persist.entities.DebitPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DebitMapper {

  @Autowired
  private UserMapper userMapper;

  public DebitResponseDTO toResponse(DebitPersist debitPersist) {
    return new DebitResponseDTO(debitPersist.getId(), debitPersist.getName(), debitPersist.getAmount(), debitPersist.getMoment(), debitPersist.getCategory().getId());
  }

  public Debit toDomain(DebitPersist debitPersist) {
    Debit debit = new Debit();

    debit.setId(debitPersist.getId());
    debit.setName(debitPersist.getName());
    debit.setAmount(debitPersist.getAmount());
    debit.setMoment(debitPersist.getMoment());
    debit.setUser(userMapper.toDomain(debitPersist.getUser()));

    if(debitPersist.getCategory() != null){
      Category category = new Category();
      category.setId(debitPersist.getCategory().getId());
      category.setName(debitPersist.getCategory().getName());
      category.setUser(debit.getUser());
    }

    return debit;
  }

  public DebitPersist toPersist(Debit debit) {
    DebitPersist debitPersist = new DebitPersist();

    debitPersist.setId(debit.getId());
    debitPersist.setName(debit.getName());
    debitPersist.setAmount(debit.getAmount());
    debitPersist.setUser(userMapper.toPersist(debit.getUser()));

    if(debit.getCategory() != null){
      CategoryPersist categoryPersist = new CategoryPersist();
      categoryPersist.setId(debit.getId());
      categoryPersist.setName(debit.getCategory().getName());
      categoryPersist.setUser(debitPersist.getUser());
    }

    return debitPersist;
  }

}
