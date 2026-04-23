package com.rafaelw.financeControl.infra.config;

import com.rafaelw.financeControl.application.ports.out.UserRepositoryPort;
import com.rafaelw.financeControl.application.usecase.UserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserUseCaseImpl userUseCase(UserRepositoryPort userRepositoryPort){
        return new UserUseCaseImpl(userRepositoryPort);
    }
}
