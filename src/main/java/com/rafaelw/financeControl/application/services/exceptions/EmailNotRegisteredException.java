package com.rafaelw.financeControl.application.services.exceptions;

public class EmailNotRegisteredException extends RuntimeException {

  public EmailNotRegisteredException() {
    super("Email Not Registered");
  }
}
