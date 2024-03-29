package com.eb.server.unit.api.v1.mapper;

import com.eb.server.GameFixtures;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.Card;
import com.eb.server.domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserMapperTest {

    public static final String NAME = "Johan";
    public static final Long GAME_ID = 2L;
    public static final Long ID = 1L;

    UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    public void userToUserDTO() {
        List<Card> deck =  GameFixtures.deck();

        User user = new User();
        user.setGameId(GAME_ID);
        user.setId(ID);
        user.setName(NAME);
        user.setDeck(deck);

        UserDTO userDTO = userMapper.userToUserDTO(user);

        assertEquals(ID, userDTO.getId());
        assertEquals(NAME, userDTO.getName());
        assertEquals(deck.size(), userDTO.getDeck().size());
        assertEquals(deck.get(0).getId(), userDTO.getDeck().get(0));
        assertEquals(GAME_ID, userDTO.getGameId());
    }

    @Test
    public void userDTOToUser() {
        List<Long> deck = GameFixtures.deck().stream().map(Card::getId).collect(Collectors.toList());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setName(NAME);
        userDTO.setDeck(deck);

        User user = userMapper.userDTOToUser(userDTO);

        assertEquals(NAME, user.getName());
        assertTrue(user.getDeck().isEmpty());
    }
}
