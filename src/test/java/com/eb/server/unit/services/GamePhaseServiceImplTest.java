package com.eb.server.unit.services;

import com.eb.server.domain.Game;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.domain.GamePlayer;
import com.eb.server.services.GamePhaseService;
import com.eb.server.services.GamePhaseServiceImpl;
import com.eb.server.GameFixtures;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class GamePhaseServiceImplTest {
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
    public void handleEndGame() {
        Game game = GameFixtures.game();
        game.getGamePlayers().get(0).setHealth(10);
        game.setGamePhase(GameFixtures.gamePhase(GamePhaseType.PHASE_BATTLE));
        gamePhaseService.handleCommand(game, GameFixtures.endCommand());
        gamePhaseService.handleCommand(game, GameFixtures.endCommand());
        assertEquals(GamePhaseType.PHASE_OUTCOME, game.getGamePhase().getGamePhaseType());
        gamePhaseService.handleCommand(game, GameFixtures.endCommand());
        assertEquals(GamePhaseType.PHASE_NONE, game.getGamePhase().getGamePhaseType());
    }

    @Test
    public void handleCommandDraw() {
        Game game = GameFixtures.game();
        game.setGamePhase(GameFixtures.gamePhase(GamePhaseType.PHASE_GATHER));

        gamePhaseService.handleCommand(game, GameFixtures.drawCommand("5"));

        assertEquals(25, game.getGamePlayers().get(1).getDeck().size());
    }

    @Test
    public void fullLoop() {
        Integer BOT_DAMAGE_LEFT = 50;
        Integer PLAYER_DAMAGE_LEFT = 150;

        Game game = GameFixtures.game();
        game.setGamePhase(GameFixtures.gamePhase(GamePhaseType.PHASE_GATHER));

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getGamePhaseType());

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_BATTLE_PREPARATION, game.getGamePhase().getGamePhaseType());

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_BATTLE, game.getGamePhase().getGamePhaseType());

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_BATTLE_RESOLUTION, game.getGamePhase().getGamePhaseType());
        assertEquals(BOT_DAMAGE_LEFT, game.getGamePlayers().get(0).getHealth());
        assertEquals(PLAYER_DAMAGE_LEFT, game.getGamePlayers().get(1).getHealth());

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_GATHER, game.getGamePhase().getGamePhaseType());
    }
}
