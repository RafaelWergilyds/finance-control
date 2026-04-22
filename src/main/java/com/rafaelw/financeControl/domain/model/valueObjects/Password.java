package com.rafaelw.financeControl.domain.model.valueObjects;
import org.springframework.security.crypto.password.PasswordEncoder;

public record Password(String hashedPassword) {

    public static Password create(String rawPassword, PasswordEncoder passwordEncoder){
        if(rawPassword == null || rawPassword.length() < 8){
            throw new IllegalArgumentException("the password must be at least 8 characters");
        }

        String hashedValue = passwordEncoder.encode(rawPassword);
        return new Password(hashedValue);
    }
}
