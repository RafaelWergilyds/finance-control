package com.rafaelw.financeControl.infra.config;

import com.rafaelw.financeControl.application.ports.out.CategoryRepositoryPort;
import com.rafaelw.financeControl.application.ports.out.UserRepositoryPort;
import com.rafaelw.financeControl.application.service.CategoryServiceImpl;
import com.rafaelw.financeControl.application.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserServiceImpl userService(UserRepositoryPort userRepositoryPort){
        return new UserServiceImpl(userRepositoryPort);
    }

    @Bean
    public CategoryServiceImpl categoryService(CategoryRepositoryPort categoryRepositoryPort){
        return new CategoryServiceImpl(categoryRepositoryPort);
    }
}
