package com.eb.server.integration;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.repositories.CardRepository;
import com.eb.server.repositories.GameRepository;
import com.eb.server.repositories.UserRepository;
import com.eb.server.services.*;
import com.eb.server.services.phases.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BootstrapIntegrationTest {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;

    Bootstrap bootstrap;
    UserService userService;
    GameService gameService;
    GamePhaseService gamePhaseService;

    @Before
    public void setUp() throws Exception {
        bootstrap = new Bootstrap(userRepository, cardRepository);
        bootstrap.run();

        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository, bootstrap);
        gamePhaseService = new GamePhaseServiceImpl(
                new PhaseHandlerNone(),
                new PhaseHandlerGather(),
                new PhaseHandlerPlan(),
                new PhaseHandlerBattlePreparation(),
                new PhaseHandlerBattle(),
                new PhaseHandlerBattleResolution(),
                new PhaseHandlerOutcome()
        );
        gameService = new GameServiceImpl(GameMapper.INSTANCE, gameRepository, gamePhaseService, userService);

    }

    @Test
    // TODO: discover why we cannot create 2 tests
    public void testBotGame() {
        UserDTO bot = userService.findUserDTOById(Bootstrap.BOT_ID);
        assertEquals(Bootstrap.BOT_NAME, bot.getName());
        assertEquals(bootstrap.getDefaultDeck().size(), bot.getDeck().size());

        Long GAME_ID = 1L;

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Johan");

        UserDTO savedUserDTO = userService.createNewUser(userDTO);

        RequestGameDTO requestGameDTO = new RequestGameDTO();
        requestGameDTO.setUserId(savedUserDTO.getId());
        GameDTO newGame = gameService.requestNewGame(requestGameDTO);

        assertEquals(GAME_ID, newGame.getId());

        GameDTO retrievedNewGame = gameService.findGameDTOById(newGame.getId());
        assertEquals(GamePhaseType.PHASE_GATHER, retrievedNewGame.getGamePhase().getType());
        assertEquals(2, retrievedNewGame.getGamePlayers().size());
        assertEquals(5, retrievedNewGame.getGamePlayers().get(0).getHand().size());
        assertEquals(1, retrievedNewGame.getGamePlayers().get(0).getHand().get(0).getAttributes().size());

        UserDTO userWithGameDTO = userService.findUserDTOById(savedUserDTO.getId());
        assertEquals(GAME_ID, userWithGameDTO.getGameId());

        // the user dispatch a draw command
        GameCommandDTO gameCommandDTO = new GameCommandDTO();
        gameCommandDTO.setUserId(savedUserDTO.getId());
        gameCommandDTO.setPayload("5");
        gameCommandDTO.setType("COMMAND_DRAW");

        GameDTO drawCommandGame = gameService.handleCommand(newGame.getId(), gameCommandDTO);

        assertEquals(25, drawCommandGame.getGamePlayers().get(1).getDeck().size());

        gameCommandDTO.setUserId(savedUserDTO.getId());
        gameCommandDTO.setPayload("");
        gameCommandDTO.setType("COMMAND_HARVEST");

        GameDTO gatherCommandGame = gameService.handleCommand(newGame.getId(), gameCommandDTO);

        assertEquals(Integer.valueOf(1), gatherCommandGame.getGamePlayers().get(1).getEnergy());

        gameCommandDTO.setUserId(savedUserDTO.getId());
        gameCommandDTO.setPayload("");
        gameCommandDTO.setType("COMMAND_END");

        GameDTO endCommandGame = gameService.handleCommand(newGame.getId(), gameCommandDTO);

        assertEquals(GamePhaseType.PHASE_PLAN, endCommandGame.getGamePhase().getType());

        // TODO: test play a card

    }
}
