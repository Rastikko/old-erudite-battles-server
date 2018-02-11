package com.eb.server.integration;

import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.User;
import com.eb.server.domain.types.GameType;
import com.eb.server.domain.types.UserStateType;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
public class VsPlayerIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void testVsGame() {
        UserDTO userDTO = testCreatePlayer("Johan");
        UserDTO otherUserDTO = testCreatePlayer("Johan2");

        /* FIND GAME */
        RequestGameDTO requestGameDTO = new RequestGameDTO();
        requestGameDTO.setUserId(userDTO.getId());
        requestGameDTO.setType(GameType.VS_PLAYER);
        GameDTO newGame = gameService.requestNewGame(requestGameDTO);
        assertNull(newGame);
        User userFindGame = userService.findUserByID(userDTO.getId());
        assertEquals(UserStateType.SEARCHING_GAME, userFindGame.getState());

        /* CREATE GAME */
        RequestGameDTO otherUserRequestGameDTO = new RequestGameDTO();
        otherUserRequestGameDTO.setUserId(otherUserDTO.getId());
        otherUserRequestGameDTO.setType(GameType.VS_PLAYER);
        GameDTO otherUserNewGame = gameService.requestNewGame(otherUserRequestGameDTO);
        assertNotNull(otherUserNewGame);
        assertThat( otherUserNewGame.getGamePlayers(), contains(
                hasProperty("userId", is(otherUserDTO.getId())),
                hasProperty("userId", is(userDTO.getId()))
        ));
        User foundGameUser = userService.findUserByID(userDTO.getId());
        assertEquals(UserStateType.IN_GAME, foundGameUser.getState());
    }
}
