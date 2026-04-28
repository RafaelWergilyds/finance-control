package com.rafaelw.financeControl.infra.inbound.rest;

import com.rafaelw.financeControl.infra.inbound.rest.dto.auth.AuthenticationRequest;
import com.rafaelw.financeControl.infra.inbound.rest.dto.auth.AuthenticationResponse;
import com.rafaelw.financeControl.application.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User authentication operation")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @Operation(summary = "Authenticate a user", description = "Generates an access token for an existing user")
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest data) {
    String token = authenticationService.login(data.email(), data.password());
    return ResponseEntity.ok(new AuthenticationResponse(token));
  }

}
