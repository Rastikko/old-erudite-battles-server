package com.eb.server.integration;

import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.User;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.domain.types.GameType;
import com.eb.server.domain.types.UserStateType;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
public class VsPlayerIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void testVsGame() {
        GameDTO game;

        UserDTO userDTO = testCreatePlayer("Johan");
        UserDTO otherUserDTO = testCreatePlayer("Johan2");
        GameCommandDTO endCommandDTO = gameCommandDTO(userDTO.getId(), "COMMAND_END", "");
        GameCommandDTO otherEndCommandDTO = gameCommandDTO(otherUserDTO.getId(), "COMMAND_END", "");

        /* FIND GAME */
        RequestGameDTO requestGameDTO = new RequestGameDTO();
        requestGameDTO.setUserId(userDTO.getId());
        requestGameDTO.setType(GameType.VS_PLAYER);
        game = gameService.requestNewGame(requestGameDTO);
        assertNull(game);
        User userFindGame = userService.findUserByID(userDTO.getId());
        assertEquals(UserStateType.SEARCHING_GAME, userFindGame.getState());
        RequestGameDTO otherUserRequestGameDTO = new RequestGameDTO();
        otherUserRequestGameDTO.setUserId(otherUserDTO.getId());
        otherUserRequestGameDTO.setType(GameType.VS_PLAYER);

        /* CREATE GAME */
        game = gameService.requestNewGame(otherUserRequestGameDTO);
        assertNotNull(game);
        assertThat( game.getGamePlayers(), contains(
                hasProperty("userId", is(otherUserDTO.getId())),
                hasProperty("userId", is(userDTO.getId()))
        ));
        User foundGameUser = userService.findUserByID(userDTO.getId());
        assertEquals(UserStateType.IN_GAME, foundGameUser.getState());

        /* PHASE GATHER */
        game = gameService.handleCommand(game.getId(), gameCommandDTO(userDTO.getId(), "COMMAND_DRAW", "5"));
        game = gameService.handleCommand(game.getId(), gameCommandDTO(otherUserDTO.getId(), "COMMAND_DRAW", "5"));
        assertEquals(Integer.valueOf(25), game.getGamePlayers().get(0).getDeck());
        assertEquals(Integer.valueOf(25), game.getGamePlayers().get(1).getDeck());
        game = gameService.handleCommand(game.getId(), gameCommandDTO(userDTO.getId(), "COMMAND_HARVEST", ""));
        game = gameService.handleCommand(game.getId(), gameCommandDTO(otherUserDTO.getId(), "COMMAND_HARVEST", ""));
        assertEquals(Integer.valueOf(5), game.getGamePlayers().get(0).getEnergy());
        assertEquals(Integer.valueOf(5), game.getGamePlayers().get(1).getEnergy());

        /* PLAN 1 */
        game = gameService.handleCommand(game.getId(), endCommandDTO);
        game = gameService.handleCommand(game.getId(), otherEndCommandDTO);
        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        assertEquals("{\"planTurnGamePlayerId\":1,\"playedCardId\":0,\"skipPlanTurn\":false}", game.getGamePhase().getPayload());
        game = gameService.handleCommand(game.getId(), gameCommandDTO(userDTO.getId(), "COMMAND_PLAY_CARD", "{\"cardId\":1}"));
        assertEquals("{\"planTurnGamePlayerId\":1,\"playedCardId\":1,\"skipPlanTurn\":false}", game.getGamePhase().getPayload());

        /* PLAN 2 */
        // TODO: fixme
//        game = gameService.handleCommand(game.getId(), endCommandDTO);
//        game = gameService.handleCommand(game.getId(), otherEndCommandDTO);
//        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
//        assertEquals("", game.getGamePhase().getPayload());
//        game = gameService.handleCommand(game.getId(), gameCommandDTO(otherUserDTO.getId(), "COMMAND_PLAY_CARD", "{\"cardId\":1}"));
//
//        /* PLAN 3 */
//        game = gameService.handleCommand(game.getId(), endCommandDTO);
//        game = gameService.handleCommand(game.getId(), otherEndCommandDTO);
//        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
//        assertEquals("", game.getGamePhase().getPayload());
//
//        /* BATTLE PREPARATION */
//        game = gameService.handleCommand(game.getId(), endCommandDTO);
//        game = gameService.handleCommand(game.getId(), otherEndCommandDTO);
//        assertEquals(GamePhaseType.PHASE_BATTLE_PREPARATION, game.getGamePhase().getType());

    }
}
