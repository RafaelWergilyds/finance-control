package com.rafaelw.financeControl.application.mappers;

import com.rafaelw.financeControl.domain.model.entities.User;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.UserPersist;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserPersist userPersist){
        User user = new User();

        user.setId(userPersist.getId());
        user.setName(userPersist.getName());
        user.changeEmail(userPersist.getEmail());
        user.changePassword(userPersist.getPassword());
        user.setRole(userPersist.getRole());
        return user;
    }

    public UserPersist toPersist(User user){
        UserPersist userPersist = new UserPersist();

        userPersist.setId(user.getId());
        userPersist.setName(user.getName());
        userPersist.setEmail(user.getEmail());
        userPersist.setPassword(user.getPassword());
        userPersist.setRole(user.getRole());
        return userPersist;
    }
}
