package com.eb.server.unit.services;

import com.eb.server.domain.Game;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.domain.GamePlayer;
import com.eb.server.services.GamePhaseService;
import com.eb.server.services.GamePhaseServiceImpl;
import com.eb.server.GameFixtures;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class GamePhaseServiceImplTest {
    // TODO: these are integration tests
    /*GamePhaseService gamePhaseService;


    @Before
    public void setUp()
    {
        gamePhaseService = new GamePhaseServiceImpl();
    }

    @Test
    public void handleNewGame() {
        Integer BOT_ENERGY = 1;
        int BOT_DECK = 25;
        int BOT_HAND = 5;
        Game game = GameFixtures.game();

        gamePhaseService.handleNewGame(game);
        GamePlayer bot = game.getGamePlayers().get(0);
        assertEquals(game.getGamePhase().getType(), GamePhaseType.PHASE_GATHER);
        // will process bot draw cards
        assertEquals(BOT_DECK, bot.getDeck().size());
        assertEquals(BOT_HAND, bot.getHand().size());
        assertEquals(BOT_ENERGY, bot.getEnergy());
    }

    @Test
    public void handleEndGame() {
        Game game = GameFixtures.game();
        game.getGamePlayers().get(0).setHealth(10);
        game.setGamePhase(GameFixtures.gamePhase(GamePhaseType.PHASE_BATTLE));
        gamePhaseService.handleCommand(game, GameFixtures.endCommand());
        gamePhaseService.handleCommand(game, GameFixtures.endCommand());
        assertEquals(GamePhaseType.PHASE_OUTCOME, game.getGamePhase().getType());
        gamePhaseService.handleCommand(game, GameFixtures.endCommand());
        assertEquals(GamePhaseType.PHASE_NONE, game.getGamePhase().getType());
    }

    @Test
    public void fullLoop() {
        Integer BOT_DAMAGE_LEFT = 50;
        Integer PLAYER_DAMAGE_LEFT = 150;

        Game game = GameFixtures.game();
        game.setGamePhase(GameFixtures.gamePhase(GamePhaseType.PHASE_GATHER));

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_BATTLE_PREPARATION, game.getGamePhase().getType());

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_BATTLE, game.getGamePhase().getType());

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_BATTLE_RESOLUTION, game.getGamePhase().getType());
        assertEquals(BOT_DAMAGE_LEFT, game.getGamePlayers().get(0).getHealth());
        assertEquals(PLAYER_DAMAGE_LEFT, game.getGamePlayers().get(1).getHealth());

        gamePhaseService.handleCommand(game, GameFixtures.endCommand());

        assertEquals(GamePhaseType.PHASE_GATHER, game.getGamePhase().getType());
    }*/
}
