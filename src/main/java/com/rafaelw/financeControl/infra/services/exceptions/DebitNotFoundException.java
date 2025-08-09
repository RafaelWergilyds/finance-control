package com.rafaelw.financeControl.infra.services.exceptions;

public class DebitNotFoundException extends NotFoundException {

  public DebitNotFoundException(Long id) {
    super(String.format("Debit with id %s not found", id));
  }
}
