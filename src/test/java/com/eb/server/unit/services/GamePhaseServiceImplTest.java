package com.eb.server.unit.services;

import com.eb.server.domain.Game;
import com.eb.server.domain.GamePhaseType;
import com.eb.server.services.GamePhaseService;
import com.eb.server.services.GamePhaseServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GamePhaseServiceImplTest {
    GamePhaseService gamePhaseService;

    @Before
    public void setUp() {
        gamePhaseService = new GamePhaseServiceImpl();
    }

    @Test
    public void handleNewGame() {
        Game game = new Game();

        gamePhaseService.handleNewGame(game);
        assertEquals(game.getGamePhase().getGamePhaseType(), GamePhaseType.PHASE_GATHER);
    }
}
