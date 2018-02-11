package com.eb.server.integration;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.bootstrap.Bootstrap;
import com.eb.server.domain.User;
import com.eb.server.domain.types.GameType;
import com.eb.server.domain.types.UserStateType;
import com.eb.server.repositories.*;
import com.eb.server.services.*;
import com.eb.server.services.phases.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
public class VsPlayerIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void testVsGame() {
        Long USER_ID = 2L;
        Long OTHER_USER_ID = 3L;

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Johan1");
        UserDTO savedUserDTO = userService.createNewUser(userDTO);
        UserDTO otherUserDTO = new UserDTO();
        userDTO.setName("Johan2");
        UserDTO savedOtherUserDTO = userService.createNewUser(otherUserDTO);
        // check the default deck is created
        assertEquals(USER_ID, savedUserDTO.getId());
        assertEquals(OTHER_USER_ID, savedOtherUserDTO.getId());

        /* FIND GAME */
        RequestGameDTO requestGameDTO = new RequestGameDTO();
        requestGameDTO.setUserId(USER_ID);
        requestGameDTO.setType(GameType.VS_PLAYER);
        GameDTO newGame = gameService.requestNewGame(requestGameDTO);
        assertNull(newGame);
        User userFindGame = userService.findUserByID(USER_ID);
        assertEquals(UserStateType.SEARCHING_GAME, userFindGame.getState());

        /* CREATE GAME */
        RequestGameDTO otherUserRequestGameDTO = new RequestGameDTO();
        otherUserRequestGameDTO.setUserId(OTHER_USER_ID);
        otherUserRequestGameDTO.setType(GameType.VS_PLAYER);
        GameDTO otherUserNewGame = gameService.requestNewGame(otherUserRequestGameDTO);
        assertNotNull(otherUserNewGame);
        assertThat( otherUserNewGame.getGamePlayers(), contains(
                hasProperty("userId", is(OTHER_USER_ID)),
                hasProperty("userId", is(USER_ID))
        ));
        User foundGameUser = userService.findUserByID(USER_ID);
        assertEquals(UserStateType.IN_GAME, foundGameUser.getState());
    }
}
