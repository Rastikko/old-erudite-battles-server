package com.eb.server.integration;

import com.eb.server.GameFixtures;
import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.bootstrap.Bootstrap;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.repositories.*;
import com.eb.server.services.*;
import com.eb.server.services.phases.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VsBotIntegrationTest {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    MatchmakingRequestRepository matchmakingRequestRepository;
    @Autowired
    AttributeRepository attributeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ResourceLoader resourceLoader;

    Bootstrap bootstrap;
    UserService userService;
    GameService gameService;
    GamePhaseService gamePhaseService;
    QuestionService questionService;

    @Before
    @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
    public void setUp() throws Exception {
        bootstrap = new Bootstrap(resourceLoader, userRepository, attributeRepository, cardRepository, questionRepository);
        bootstrap.run();

        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository, bootstrap);
        questionService = new QuestionServiceImpl(questionRepository);
        gamePhaseService = new GamePhaseServiceImpl(
                new PhaseHandlerGather(),
                new PhaseHandlerPlan(),
                new PhaseHandlerBattlePreparation(),
                new PhaseHandlerBattle(questionService),
                new PhaseHandlerBattleResolution(),
                new PhaseHandlerOutcome()
        );

        gameService = new GameServiceImpl(GameMapper.INSTANCE, gameRepository, matchmakingRequestRepository, gamePhaseService, userService);
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
        // check the default deck is created
        assertEquals(30, savedUserDTO.getDeck().size());

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
        GameCommandDTO drawCommandDTO = GameFixtures.gameCommandDTO(savedUserDTO.getId(), "COMMAND_DRAW", "5");
        GameDTO drawCommandGame = gameService.handleCommand(newGame.getId(), drawCommandDTO);
        assertEquals(Integer.valueOf(25), drawCommandGame.getGamePlayers().get(0).getDeck());
        assertEquals(Integer.valueOf(25), drawCommandGame.getGamePlayers().get(1).getDeck());
        assertNotNull(drawCommandGame.getGamePlayers().get(1).getHand().get(0).getAttributes().get(0).getType());
        GameCommandDTO harvestCommandDTO = GameFixtures.gameCommandDTO(savedUserDTO.getId(), "COMMAND_HARVEST", "");
        GameDTO gatherCommandGame = gameService.handleCommand(newGame.getId(), harvestCommandDTO);
        assertEquals(Integer.valueOf(5), gatherCommandGame.getGamePlayers().get(1).getEnergy());
        GameCommandDTO endCommandDTO = GameFixtures.gameCommandDTO(savedUserDTO.getId(), "COMMAND_END", "");

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
        assertNotNull(phaseBattleGame.getGamePlayers().get(0).getCurrentGameQuestion().getQuestion().getTitle());
        assertEquals(Long.valueOf(2L), phaseBattleGame.getGamePlayers().get(1).getCurrentGameQuestion().getId());
        GameCommandDTO answerCommandDTO = GameFixtures.gameCommandDTO(savedUserDTO.getId(), "COMMAND_ANSWER", "");
        GameDTO answeredBattleGame = gameService.handleCommand(newGame.getId(), answerCommandDTO);
        assertEquals(null, answeredBattleGame.getGamePlayers().get(1).getCurrentGameQuestion());
        assertEquals(Integer.valueOf(0), answeredBattleGame.getGamePlayers().get(1).getGameQuestions().get(0).getPerformance());

        /* BATTLE_RESOLUTION */
        GameDTO phaseBattleResolutionGame = gameService.handleCommand(newGame.getId(), endCommandDTO);
        assertEquals(Integer.valueOf(150), phaseBattleResolutionGame.getGamePlayers().get(0).getHealth());
        assertEquals(Integer.valueOf(120), phaseBattleResolutionGame.getGamePlayers().get(1).getHealth());
    }
}
