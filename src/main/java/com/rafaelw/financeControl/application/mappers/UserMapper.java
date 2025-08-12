package com.rafaelw.financeControl.application.mappers;

import com.rafaelw.financeControl.application.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.application.mappers.struct.AvoidContext;
import com.rafaelw.financeControl.application.mappers.struct.UserMapperStruct;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  @Autowired
  private UserMapperStruct mapperStruct;

  public UserResponseDTO toResponseDTO(UserPersist userPersist) {
    return mapperStruct.toResponseDTO(userPersist);
  }

  public UserResponseDTO toResponseDTO(User user) {
    return mapperStruct.toResponseDTO(user);
  }

  public User toDomain(UserPersist userPersist) {
    return mapperStruct.toDomain(userPersist, new AvoidContext());
  }

  public UserPersist toPersist(User user) {
    return mapperStruct.toPersist(user, new AvoidContext());
  }

}
