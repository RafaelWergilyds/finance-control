package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.dto.user.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponseDTO(UserEntity userEntity);
    UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "debits", ignore = true)
    User toUser(UserEntity userEntity);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "debits", ignore = true)
    UserEntity toUserEntity(User user);


}
