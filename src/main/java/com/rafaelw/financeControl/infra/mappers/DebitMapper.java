package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.infra.db.entities.DebitPersist;
import com.rafaelw.financeControl.infra.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.infra.mappers.struct.AvoidContext;
import com.rafaelw.financeControl.infra.mappers.struct.DebitMapperStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebitMapper {

  @Autowired
  private DebitMapperStruct mapperStruct;

  public DebitResponseDTO toResponse(Debit debit) {
    return mapperStruct.toResponse(debit);
  }

  public DebitResponseDTO toResponse(DebitPersist debitPersist) {
    return mapperStruct.toResponse(debitPersist);
  }

  public Debit toDomain(DebitPersist debitPersist) {
    return mapperStruct.toDomain(debitPersist, new AvoidContext());
  }

  public DebitPersist toPersist(Debit debit) {
    return mapperStruct.toPersist(debit, new AvoidContext());
  }

}
