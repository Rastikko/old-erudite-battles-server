package com.eb.server.unit.api.v1.mapper;

import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.Card;
import com.eb.server.domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    public static final String NAME = "Johan";
    public static final long ID = 1L;

    UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    public void userToUserDTO() throws Exception {
        Card card = new Card();
        List<Card> deck = new ArrayList<>();

        card.setName("Johan's Card");
        card.setId(1L);

        deck.add(card);

        //given
        User user = new User();
        user.setId(ID);
        user.setName(NAME);

        user.setDeck(deck);

        //when
        UserDTO userDTO = userMapper.userToUserDTO(user);

        //then
        assertEquals(Long.valueOf(ID), userDTO.getId());
        assertEquals(NAME, userDTO.getName());
//        assertEquals(deck.size(), userDTO.getDeck().size());
//        assertEquals(deck.get(0).getId(), userDTO.getDeck().get(0));
    }
}
