package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DebitMapper {

    @Mapping(target = "debit.users", ignore = true)
    @Mapping(target = "debit.categories", ignore = true)
    @Mapping(target = "category", ignore = true)
    Debit toDebit(DebitEntity debitEntity);

    @Mapping(target = "debit.users", ignore = true)
    @Mapping(target = "debit.categories", ignore = true)
    @Mapping(target = "category", ignore = true)
    DebitEntity toDebitEntity(Debit debit);

}
