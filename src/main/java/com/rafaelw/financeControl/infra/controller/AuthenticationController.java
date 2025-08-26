package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.application.dto.auth.AuthenticationRequest;
import com.rafaelw.financeControl.application.dto.auth.AuthenticationResponse;
import com.rafaelw.financeControl.application.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @Value("${jwt.expiration.time}")
  private long expiresIn;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest data) {
    String token = authenticationService.login(data.email(), data.password());
    return ResponseEntity.ok(new AuthenticationResponse(token, expiresIn));
  }

}
