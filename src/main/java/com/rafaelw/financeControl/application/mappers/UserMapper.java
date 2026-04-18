package com.rafaelw.financeControl.application.mappers;

import com.rafaelw.financeControl.application.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.application.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserPersist userPersist){
        User user = new User();

        user.setId(userPersist.getId());
        user.setName(userPersist.getName());
        user.changeEmail(userPersist.getEmail());
        user.changePassword(userPersist.getPassword());
        return user;
    }

    public UserPersist toPersist(User user){
        UserPersist userPersist = new UserPersist();

        userPersist.setId(user.getId());
        userPersist.setName(user.getName());
        userPersist.setEmail(user.getEmail());
        userPersist.setPassword(user.getPassword());
        return userPersist;
    }

    public UserResponseDTO toResponseDTO(UserPersist userPersist){
        return new UserResponseDTO(userPersist.getId(), userPersist.getName(), userPersist.getEmail(), userPersist.getRole(), userPersist.isActive());
    }
}
