package com.eb.server.integration;

import com.eb.server.GameFixtures;
import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.types.GameCommandType;
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
        /* NEW USER */
        UserDTO bot = userService.findUserDTOById(Bootstrap.BOT_ID);
        assertEquals(Bootstrap.BOT_NAME, bot.getName());
        assertEquals(bootstrap.getDefaultDeck().size(), bot.getDeck().size());
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Johan");
        UserDTO savedUserDTO = userService.createNewUser(userDTO);

        /* FIND GAME */
        RequestGameDTO requestGameDTO = new RequestGameDTO();
        requestGameDTO.setUserId(savedUserDTO.getId());
        GameDTO newGame = gameService.requestNewGame(requestGameDTO);
        assertEquals(Long.valueOf(1L), newGame.getId());

        GameDTO retrievedNewGame = gameService.findGameDTOById(newGame.getId());
        assertEquals(GamePhaseType.PHASE_GATHER, retrievedNewGame.getGamePhase().getType());
        assertEquals(2, retrievedNewGame.getGamePlayers().size());
        assertEquals(5, retrievedNewGame.getGamePlayers().get(0).getHand().size());
        assertEquals(1, retrievedNewGame.getGamePlayers().get(0).getHand().get(0).getAttributes().size());
        UserDTO userWithGameDTO = userService.findUserDTOById(savedUserDTO.getId());
        assertEquals(Long.valueOf(1L), userWithGameDTO.getGameId());

        /* GATHER */
        GameCommandDTO drawCommandDTO = getCommandDTO(savedUserDTO.getId(), "COMMAND_DRAW", "5");
        GameDTO drawCommandGame = gameService.handleCommand(newGame.getId(), drawCommandDTO);
        assertEquals(Integer.valueOf(25), drawCommandGame.getGamePlayers().get(1).getDeck());
        GameCommandDTO harvestCommandDTO = getCommandDTO(savedUserDTO.getId(), "COMMAND_HARVEST", "");
        GameDTO gatherCommandGame = gameService.handleCommand(newGame.getId(), harvestCommandDTO);
        assertEquals(Integer.valueOf(1), gatherCommandGame.getGamePlayers().get(1).getEnergy());
        GameCommandDTO endCommandDTO = getCommandDTO(savedUserDTO.getId(), "COMMAND_END", "");

        /* PLAN */
        GameDTO phasePlanGame = gameService.handleCommand(newGame.getId(), endCommandDTO);
        // TODO: test play a card
        assertEquals(GamePhaseType.PHASE_PLAN, phasePlanGame.getGamePhase().getType());

        /* BATTLE_PREPARATION */
        GameDTO phaseBattlePreparationGame = gameService.handleCommand(newGame.getId(), endCommandDTO);
        assertEquals(GamePhaseType.PHASE_BATTLE_PREPARATION, phaseBattlePreparationGame.getGamePhase().getType());

        /* BATTLE */
        GameDTO phaseBattleGame = gameService.handleCommand(newGame.getId(), endCommandDTO);
        assertEquals(GamePhaseType.PHASE_BATTLE, phaseBattleGame.getGamePhase().getType());
        assertEquals(Long.valueOf(1L), phaseBattleGame.getGamePlayers().get(0).getCurrentGameQuestion().getId());
        assertEquals(Long.valueOf(2L), phaseBattleGame.getGamePlayers().get(1).getCurrentGameQuestion().getId());
    }

    private GameCommandDTO getCommandDTO(Long userId, String commandType, String payload) {
        GameCommandDTO command = new GameCommandDTO();
        command.setUserId(userId);
        command.setType(commandType);
        command.setPayload(payload);
        return command;
    }
}
