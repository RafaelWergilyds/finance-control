package com.rafaelw.financeControl.infra.controller.exceptions;
import com.rafaelw.financeControl.domain.service.exceptions.InvalidCredentials;
import com.rafaelw.financeControl.infra.services.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<StandardError> resourceNotFound(NotFoundException e, HttpServletRequest request){
    String error = "Resource Not Found";
    HttpStatus status = HttpStatus.NOT_FOUND;
    StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(InvalidCredentials.class)
  public ResponseEntity<StandardError> invalidCredentials(InvalidCredentials e, HttpServletRequest request){
    String error = "Invalid Credentials";
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
    String error = "Method Argument Not Valid";
    String getError = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError err = new StandardError(Instant.now(), status.value(), error, getError, request.getRequestURI());

    return ResponseEntity.status(status).body(err);
  }


}
