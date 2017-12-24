package com.eb.server.api.v1.mapper;

import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
}
