package com.rafaelw.financeControl.application.mappers.struct;

import com.rafaelw.financeControl.application.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.infra.persist.entities.DebitPersist;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DebitMapperStruct {

  @Mapping(source = "category.id", target = "categoryId")
  DebitResponseDTO toResponse(Debit debit);

  @Mapping(source = "category.id", target = "categoryId")
  DebitResponseDTO toResponse(DebitPersist debitPersist);

  Debit toDomain(DebitPersist debitPersist, @Context AvoidContext context);

  DebitPersist toPersist(Debit debit, @Context AvoidContext context);

}
