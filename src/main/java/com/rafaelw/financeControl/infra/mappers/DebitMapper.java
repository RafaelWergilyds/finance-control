package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.infra.db.entities.DebitPersist;
import com.rafaelw.financeControl.infra.dto.debit.DebitResponseDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DebitMapper {

  @Mapping(source = "category.id", target = "categoryId")
  DebitResponseDTO toResponse(Debit debit);

  @Mapping(source = "category.id", target = "categoryId")
  DebitResponseDTO toResponse(DebitPersist debitPersist);

  Debit toDomain(DebitPersist debitPersist, @Context AvoidContext context);

  DebitPersist toPersist(Debit debit, @Context AvoidContext context);

}
