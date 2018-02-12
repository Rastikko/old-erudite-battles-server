package com.eb.server.integration;

import com.eb.server.GameFixtures;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.bootstrap.Bootstrap;
import com.eb.server.domain.types.GamePhaseType;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class VsBotIntegrationTest  extends AbstractIntegrationTest{

    @Test
    // TODO: discover why we cannot create 2 tests
    public void testBotGame() {
        GameDTO game;

        UserDTO bot = userService.findUserDTOById(Bootstrap.BOT_ID);
        assertEquals(Bootstrap.BOT_NAME, bot.getName());
        assertEquals(bootstrap.getDefaultDeck().size(), bot.getDeck().size());

        /* NEW USER */
        UserDTO userDTO = testCreatePlayer("Johan");
        GameCommandDTO endCommandDTO = gameCommandDTO(userDTO.getId(), "COMMAND_END", "");

        /* FIND GAME */
        RequestGameDTO requestGameDTO = new RequestGameDTO();
        requestGameDTO.setUserId(userDTO.getId());
        game = gameService.requestNewGame(requestGameDTO);
        assertEquals(Long.valueOf(1L), game.getId());

        /* GET GAME */
        game = gameService.findGameDTOById(game.getId());
        assertEquals(GamePhaseType.PHASE_GATHER, game.getGamePhase().getType());
        assertEquals(2, game.getGamePlayers().size());
        assertEquals(5, game.getGamePlayers().get(0).getHand().size());
        assertEquals(1, game.getGamePlayers().get(0).getHand().get(0).getAttributes().size());
        UserDTO userWithGameDTO = userService.findUserDTOById(userDTO.getId());
        assertEquals(Long.valueOf(1L), userWithGameDTO.getGameId());

        /* GATHER */
        GameCommandDTO drawCommandDTO = gameCommandDTO(userDTO.getId(), "COMMAND_DRAW", "5");
        game = gameService.handleCommand(game.getId(), drawCommandDTO);
        assertEquals(Integer.valueOf(25), game.getGamePlayers().get(0).getDeck());
        assertEquals(Integer.valueOf(25), game.getGamePlayers().get(1).getDeck());
        assertNotNull(game.getGamePlayers().get(1).getHand().get(0).getAttributes().get(0).getType());
        game = gameService.handleCommand(game.getId(), gameCommandDTO(userDTO.getId(), "COMMAND_HARVEST", ""));
        assertEquals(Integer.valueOf(5), game.getGamePlayers().get(1).getEnergy());

        /* PLAN */
        game = gameService.handleCommand(game.getId(), endCommandDTO);
        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        game = gameService.handleCommand(game.getId(), gameCommandDTO(userDTO.getId(), "COMMAND_PLAY_CARD", "{\"cardId\":1}"));
        game = gameService.handleCommand(game.getId(), endCommandDTO);
        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());

        /* BATTLE_PREPARATION */
        game = gameService.handleCommand(game.getId(), endCommandDTO);
        assertEquals(GamePhaseType.PHASE_BATTLE_PREPARATION, game.getGamePhase().getType());

        /* BATTLE */
        game = gameService.handleCommand(game.getId(), endCommandDTO);
        assertEquals(GamePhaseType.PHASE_BATTLE, game.getGamePhase().getType());
        assertEquals(Long.valueOf(1L), game.getGamePlayers().get(0).getCurrentGameQuestion().getId());
        assertNotNull(game.getGamePlayers().get(0).getCurrentGameQuestion().getQuestion().getTitle());
        assertEquals(Long.valueOf(2L), game.getGamePlayers().get(1).getCurrentGameQuestion().getId());
        game = gameService.handleCommand(game.getId(), gameCommandDTO(userDTO.getId(), "COMMAND_ANSWER", ""));
        assertEquals(null, game.getGamePlayers().get(1).getCurrentGameQuestion());
        assertEquals(Integer.valueOf(0), game.getGamePlayers().get(1).getGameQuestions().get(0).getPerformance());

        /* BATTLE_RESOLUTION */
        game = gameService.handleCommand(game.getId(), endCommandDTO);
        assertEquals(Integer.valueOf(150), game.getGamePlayers().get(0).getHealth());
        assertEquals(Integer.valueOf(120), game.getGamePlayers().get(1).getHealth());
    }
}
