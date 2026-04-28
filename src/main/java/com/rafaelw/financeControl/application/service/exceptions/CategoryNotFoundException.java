package com.rafaelw.financeControl.application.service.exceptions;

public class CategoryNotFoundException extends NotFoundException {

  public CategoryNotFoundException(Long id) {
    super(String.format("Category with id %s not found", id));
  }
}
