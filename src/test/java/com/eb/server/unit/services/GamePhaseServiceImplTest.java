package com.eb.server.unit.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.domain.Game;
import com.eb.server.domain.GamePhaseType;
import com.eb.server.domain.GamePlayer;
import com.eb.server.services.GamePhaseService;
import com.eb.server.services.GamePhaseServiceImpl;
import com.eb.server.GameFixtures;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class GamePhaseServiceImplTest {
    GameMapper gameMapper = GameMapper.INSTANCE;

    GamePhaseService gamePhaseService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        gamePhaseService = new GamePhaseServiceImpl();
    }

    @Test
    public void handleNewGame() {
        Game game = GameFixtures.game();

        gamePhaseService.handleNewGame(game);
        GamePlayer bot = game.getGamePlayers().get(0);
        assertEquals(game.getGamePhase().getGamePhaseType(), GamePhaseType.PHASE_GATHER);
        // will process bot draw cards
        assertEquals(25, bot.getDeck().size());
        assertEquals(5, bot.getHand().size());
    }

    @Test
    public void handleDrawCommand() {
        Game game = GameFixtures.game();
        game.setGamePhase(GameFixtures.gamePhase(GamePhaseType.PHASE_GATHER));

        gamePhaseService.handleCommand(game, GameFixtures.drawCommand("5"));

        assertEquals(25, game.getGamePlayers().get(0).getDeck().size());
    }
}
