package com.eb.server.unit.api.v1.mapper;

import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    public static final String NAME = "Johan";
    public static final long ID = 1L;

    UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    public void userToUserDTO() throws Exception {
        //given
//        User user = new User();
//        user.setId(ID);
//        user.setName(NAME);
//
//        //when
//        UserDTO userDTO = userMapper.userToUserDTO(user);
//
//        //then
//        assertEquals(Long.valueOf(ID), userDTO.getId());
//        assertEquals(NAME, userDTO.getName());
    }
}
