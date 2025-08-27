package com.rafaelw.financeControl.application.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

  public static Long getUserId(Authentication authentication) {
    if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
      throw new IllegalArgumentException("Authentication is invalid or not a JWT.");
    }
    return Long.parseLong(jwt.getSubject());
  }


}
