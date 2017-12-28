package com.eb.server.integration;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.FindGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.controllers.v1.GameController;
import com.eb.server.domain.Game;
import com.eb.server.domain.GamePhaseType;
import com.eb.server.repositories.GameRepository;
import com.eb.server.services.GamePhaseService;
import com.eb.server.services.GamePhaseServiceImpl;
import com.eb.server.services.GameService;
import com.eb.server.services.GameServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CreateGameIntegrationTest {


    @Autowired
    GameRepository gameRepository;

    GameService gameService;

    @Before
    public void setUp() {
        GamePhaseService gamePhaseService = new GamePhaseServiceImpl();
        gameService = new GameServiceImpl(GameMapper.INSTANCE, gameRepository, gamePhaseService);
    }

    @Test
    public void createNewBotGame() {
        Long GAME_ID = 1L;

        FindGameDTO findGameDTO = new FindGameDTO();
        findGameDTO.setUserId(2L);
        GameDTO game = gameService.createNewGame(findGameDTO);

        assertEquals(GAME_ID, game.getId());
        assertEquals(GamePhaseType.PHASE_GATHER, game.getGamePhase().getGamePhaseType());
    }

}
