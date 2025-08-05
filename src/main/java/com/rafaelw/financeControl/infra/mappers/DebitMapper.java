package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitMapper {

    Debit toDebit(DebitEntity debitEntity);
    DebitEntity toDebitEntity(Debit debit);

}
