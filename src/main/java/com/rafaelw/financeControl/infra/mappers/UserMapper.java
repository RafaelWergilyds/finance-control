package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

  User toUser(UserEntity userEntity);
  UserEntity toUserEntity(User user);

}
