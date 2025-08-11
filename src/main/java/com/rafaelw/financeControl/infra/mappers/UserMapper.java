package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.db.entities.UserPersist;
import com.rafaelw.financeControl.infra.dto.user.UserResponseDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponseDTO toResponseDTO(UserPersist userPersist);

  UserResponseDTO toResponseDTO(User user);

  User toDomain(UserPersist userPersist, @Context AvoidContext context);

  UserPersist toPersist(User user, @Context AvoidContext context);


}
