package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.db.entities.UserPersist;
import com.rafaelw.financeControl.infra.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.infra.mappers.struct.AvoidContext;
import com.rafaelw.financeControl.infra.mappers.struct.UserMapperStruct;
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
