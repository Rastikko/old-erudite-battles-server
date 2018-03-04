package com.eb.server.unit.services.phases;

import com.eb.server.GameFixtures;
import com.eb.server.domain.Game;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.services.phases.PhaseHandlerGather;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhaseHandlerGatherTest {

    PhaseHandlerGather phaseHandlerGather;

    Game game;

    @Before
    public void setUp() {
        game = GameFixtures.botGame();
        game.getGamePhases().add(GameFixtures.gamePhase(GamePhaseType.PHASE_GATHER));
        game.getGamePlayers().get(1).getHand().clear();
        phaseHandlerGather = new PhaseHandlerGather();
    }

    @Test
    public void handleCommandDraw() throws Exception {
        phaseHandlerGather.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_DRAW, ""));

        assertEquals(25, game.getGamePlayers().get(1).getDeck().size());
        assertEquals(5, game.getGamePlayers().get(1).getHand().size());
    }

    @Test
    public void handleCommandDrawFullDeck() throws Exception {

        game.getGamePlayers().get(1).getCemetery().addAll(game.getGamePlayers().get(1).getDeck());
        game.getGamePlayers().get(1).getDeck().clear();
        assertEquals(0, game.getGamePlayers().get(1).getDeck().size());
        assertEquals(30, game.getGamePlayers().get(1).getCemetery().size());

        phaseHandlerGather.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_DRAW, ""));

        assertEquals(25, game.getGamePlayers().get(1).getDeck().size());
        assertEquals(5, game.getGamePlayers().get(1).getHand().size());

    }

    @Test
    public void handleCommandHarvest() throws Exception {
        Integer ENERGY_HARVESTED = 5;
        Game game = GameFixtures.botGame();
        game.getGamePhases().add((GameFixtures.gamePhase(GamePhaseType.PHASE_GATHER)));

        phaseHandlerGather.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_HARVEST, ""));

        assertEquals(ENERGY_HARVESTED, game.getGamePlayers().get(1).getEnergy());
    }
}
