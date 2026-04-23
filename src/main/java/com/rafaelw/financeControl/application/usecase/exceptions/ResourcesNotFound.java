package com.rafaelw.financeControl.application.usecase.exceptions;

public class ResourcesNotFound extends NotFoundException {

  public ResourcesNotFound() {
    super("List of Resources Not Found");
  }
}
