package com.rafaelw.financeControl.domain.model.valueObjects;

import lombok.*;

public record Email(String email) {

    public Email{
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

}
