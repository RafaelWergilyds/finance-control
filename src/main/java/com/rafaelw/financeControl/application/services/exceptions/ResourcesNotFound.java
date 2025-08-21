package com.rafaelw.financeControl.application.services.exceptions;

public class ResourcesNotFound extends NotFoundException {

  public ResourcesNotFound() {
    super("List of Resources Not Found");
  }
}
