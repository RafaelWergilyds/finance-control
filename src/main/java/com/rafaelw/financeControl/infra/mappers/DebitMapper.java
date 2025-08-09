package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import com.rafaelw.financeControl.infra.dto.debit.DebitResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DebitMapper {

  @Mapping(source = "category.id", target = "categoryId")
  DebitResponseDTO toResponse(Debit debit);

  @Mapping(source = "category.id", target = "categoryId")
  DebitResponseDTO toResponse(DebitEntity debitEntity);


  @Mapping(target = "user.debits", ignore = true)
  @Mapping(target = "user.categories", ignore = true)
  @Mapping(target = "category.debits", ignore = true)
  @Mapping(target = "category.user", ignore = true)
  Debit toDebit(DebitEntity debitEntity);

  @Mapping(target = "user.debits", ignore = true)
  @Mapping(target = "user.categories", ignore = true)
  @Mapping(target = "category.debits", ignore = true)
  @Mapping(target = "category.user", ignore = true)
  DebitEntity toDebitEntity(Debit debit);

}
