package com.rafaelw.financeControl.application.services;

import com.rafaelw.financeControl.application.services.exceptions.EmailNotRegisteredException;
import com.rafaelw.financeControl.application.services.exceptions.WrongPasswordException;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Value("${jwt.app.issuer}")
  private String issuer;

  @Value("${jwt.expiration.time}")
  private long expiresIn;

  @Autowired
  private JpaUserRepository jpaUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtEncoder jwtEncoder;

  public String login(String email, String password) {
    Instant now = Instant.now();
    Optional<UserPersist> user = jpaUserRepository.findByEmail(email);

    if (user.isEmpty()) {
      throw new EmailNotRegisteredException();
    }

    boolean isPasswordMatch = passwordEncoder.matches(password, user.get().getPassword());

    if (!isPasswordMatch) {
      throw new WrongPasswordException();
    }

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer(issuer)
        .subject(user.get().getId().toString())
        .issuedAt(now)
        .claim("name", user.get().getName())
        .claim("email", user.get().getEmail())
        .claim("role", user.get().getRole())
        .expiresAt(now.plusSeconds(expiresIn)).build();

    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}




