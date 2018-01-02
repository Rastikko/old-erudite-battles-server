package com.eb.server.integration;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.RequestGameCommandDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.GamePhaseType;
import com.eb.server.repositories.CardRepository;
import com.eb.server.repositories.GameRepository;
import com.eb.server.repositories.UserRepository;
import com.eb.server.services.*;
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
        gamePhaseService = new GamePhaseServiceImpl();
        gameService = new GameServiceImpl(GameMapper.INSTANCE, gameRepository, gamePhaseService, userService);

    }

    @Test
    public void testBotGame() {
        // TODO: discover why we cannot create 2 tests
        UserDTO bot = userService.findUserDTOById(Bootstrap.BOT_ID);
        assertEquals(Bootstrap.BOT_NAME, bot.getName());
        assertEquals(bootstrap.getDefaultDeck().size(), bot.getDeck().size());

        Long GAME_ID = 1L;

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Johan");

        UserDTO savedUserDTO = userService.createNewUser(userDTO);

        RequestGameDTO requestGameDTO = new RequestGameDTO();
        requestGameDTO.setUserId(savedUserDTO.getId());
        GameDTO newGame = gameService.createNewGame(requestGameDTO);

        assertEquals(GAME_ID, newGame.getId());

        GameDTO retrievedNewGame = gameService.findGameDTOById(newGame.getId());
        assertEquals(GamePhaseType.PHASE_GATHER, retrievedNewGame.getGamePhase().getGamePhaseType());
        assertEquals(2, retrievedNewGame.getGamePlayers().size());
        assertEquals(5, retrievedNewGame.getGamePlayers().get(0).getHand().size());
        //assertEquals(1, retrievedNewGame.getGamePlayers().get(0).getHand().get(0).getAttributes().size());

        // TODO: discover why gameId keeps returning null
        UserDTO userWithGameDTO = userService.findUserDTOById(savedUserDTO.getId());
        assertEquals(GAME_ID, userWithGameDTO.getGameId());

        // the user dispatch a draw command
        RequestGameCommandDTO requestGameCommandDTO = new RequestGameCommandDTO();
        requestGameCommandDTO.setUserId(savedUserDTO.getId());
        requestGameCommandDTO.setPayload("5");
        requestGameCommandDTO.setGameCommandType("COMMAND_DRAW");

        GameDTO drawCommandGame = gameService.handleCommand(newGame.getId(), requestGameCommandDTO);

        assertEquals(25, drawCommandGame.getGamePlayers().get(1).getDeck().size());

    }
}
