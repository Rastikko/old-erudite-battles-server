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
import com.eb.server.repositories.QuestionRepository;
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
    @Autowired
    QuestionRepository questionRepository;

    Bootstrap bootstrap;
    UserService userService;
    GameService gameService;
    GamePhaseService gamePhaseService;
    QuestionService questionService;

    @Before
    public void setUp() throws Exception {
        bootstrap = new Bootstrap(userRepository, cardRepository, questionRepository);
        bootstrap.run();

        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository, bootstrap);

        questionService = new QuestionServiceImpl(questionRepository);

        gamePhaseService = new GamePhaseServiceImpl(
                new PhaseHandlerNone(),
                new PhaseHandlerGather(),
                new PhaseHandlerPlan(),
                new PhaseHandlerBattlePreparation(),
                new PhaseHandlerBattle(questionService),
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
        GameCommandDTO drawCommandDTO = new GameCommandDTO();
        drawCommandDTO.setUserId(savedUserDTO.getId());
        drawCommandDTO.setPayload("5");
        drawCommandDTO.setType("COMMAND_DRAW");

        GameDTO drawCommandGame = gameService.handleCommand(newGame.getId(), drawCommandDTO);

        assertEquals(25, drawCommandGame.getGamePlayers().get(1).getDeck().size());

        GameCommandDTO harvestCommandDTO = new GameCommandDTO();

        harvestCommandDTO.setUserId(savedUserDTO.getId());
        harvestCommandDTO.setPayload("");
        harvestCommandDTO.setType("COMMAND_HARVEST");

        GameDTO gatherCommandGame = gameService.handleCommand(newGame.getId(), harvestCommandDTO);

        assertEquals(Integer.valueOf(1), gatherCommandGame.getGamePlayers().get(1).getEnergy());

        GameCommandDTO endCommandDTO = new GameCommandDTO();

        endCommandDTO.setUserId(savedUserDTO.getId());
        endCommandDTO.setPayload("");
        endCommandDTO.setType("COMMAND_END");

        GameDTO phasePlanGame = gameService.handleCommand(newGame.getId(), endCommandDTO);

        // TODO: test play a card
        assertEquals(GamePhaseType.PHASE_PLAN, phasePlanGame.getGamePhase().getType());

        GameDTO phaseBattlePreparationGame = gameService.handleCommand(newGame.getId(), endCommandDTO);

        assertEquals(GamePhaseType.PHASE_BATTLE_PREPARATION, phaseBattlePreparationGame.getGamePhase().getType());

        GameDTO phaseBattleGame = gameService.handleCommand(newGame.getId(), endCommandDTO);

        assertEquals(GamePhaseType.PHASE_BATTLE, phaseBattleGame.getGamePhase().getType());
        assertEquals(Long.valueOf(1L), phaseBattleGame.getGamePlayers().get(0).getCurrentGameQuestion().getId());
        assertEquals(Long.valueOf(2L), phaseBattleGame.getGamePlayers().get(1).getCurrentGameQuestion().getId());

    }
}
