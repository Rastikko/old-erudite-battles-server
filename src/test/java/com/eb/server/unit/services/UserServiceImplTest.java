package com.eb.server.unit.services;

import com.eb.server.GameFixtures;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.User;
import com.eb.server.repositories.UserRepository;
import com.eb.server.services.UserService;
import com.eb.server.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    public static final Long ID = 1L;
    public static final String NAME = "Johan";

    UserService userService;
    UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userMapper, userRepository);
    }

    @Test
    public void getUserById() throws Exception {
        User user = new User();
        user.setId(ID);
        user.setName(NAME);

        when(userRepository.findOne(anyLong())).thenReturn(user);

        UserDTO userDTO = userService.findUserDTOById(ID);

        assertEquals(ID, userDTO.getId());
        assertEquals(NAME, userDTO.getName());
    }

    @Test
    public void createNewUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(NAME);

        when(userRepository.save(any(User.class))).thenAnswer(u -> u.getArguments()[0]);
        UserDTO savedDto = userService.createNewUser(userDTO);

        assertEquals(NAME, savedDto.getName());
        assertEquals(GameFixtures.getDefaultDeck().size(), savedDto.getDeck().size());
    }
}
