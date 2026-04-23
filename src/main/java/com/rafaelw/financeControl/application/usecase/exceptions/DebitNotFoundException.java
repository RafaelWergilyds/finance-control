package com.rafaelw.financeControl.application.usecase.exceptions;

public class DebitNotFoundException extends NotFoundException {

  public DebitNotFoundException(Long id) {
    super(String.format("Debit with id %s not found", id));
  }
}
