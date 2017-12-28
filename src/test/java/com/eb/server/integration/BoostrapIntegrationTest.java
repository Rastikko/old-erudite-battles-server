package com.eb.server.integration;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.FindGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.GamePhaseType;
import com.eb.server.repositories.CardRepository;
import com.eb.server.repositories.GameRepository;
import com.eb.server.repositories.UserRepository;
import com.eb.server.services.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BoostrapIntegrationTest {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;

    UserService userService;
    GameService gameService;
    GamePhaseService gamePhaseService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(userRepository, cardRepository);
        bootstrap.run();

        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository);
        gamePhaseService = new GamePhaseServiceImpl();
        gameService = new GameServiceImpl(GameMapper.INSTANCE, gameRepository, gamePhaseService, userService);

    }

    @Test
    public void createNewBotGame() {
        // TODO: discover why we cannot create 2 tests
        UserDTO bot = userService.findUserDTOById(Bootstrap.BOT_ID);
        assertEquals(Bootstrap.BOT_NAME, bot.getName());
        assertEquals(Bootstrap.getDefaultDeck().size(), bot.getDeck().size());

        Long GAME_ID = 1L;

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Johan");

        UserDTO savedUserDTO = userService.createNewUser(userDTO);

        FindGameDTO findGameDTO = new FindGameDTO();
        findGameDTO.setUserId(savedUserDTO.getId());
        GameDTO game = gameService.createNewGame(findGameDTO);

        assertEquals(GAME_ID, game.getId());
        assertEquals(GamePhaseType.PHASE_GATHER, game.getGamePhase().getGamePhaseType());
        assertEquals(2, game.getGamePlayers().size());
//        assertEquals(25, game.getGamePlayers().get(0).getDeck());
    }
}
