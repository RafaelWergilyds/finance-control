package com.rafaelw.financeControl.application.mappers.struct;

import com.rafaelw.financeControl.application.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperStruct {

  UserResponseDTO toResponseDTO(UserPersist userPersist);

  UserResponseDTO toResponseDTO(User user);

  User toDomain(UserPersist userPersist, @Context AvoidContext context);

  UserPersist toPersist(User user, @Context AvoidContext context);


}
