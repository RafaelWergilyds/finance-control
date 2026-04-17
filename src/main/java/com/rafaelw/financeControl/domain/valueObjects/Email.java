package com.rafaelw.financeControl.domain.valueObjects;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Email {
    private String email;

    public Email(String email){
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.email = email;
    }

}
