package com.rafaelw.financeControl.infra.services.exceptions;

public class CategoryNotFoundException extends NotFoundException {

  public CategoryNotFoundException(Long id) {
    super(String.format("Category with id %s not found", id));
  }
}
