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

    @Before
    public void setUp() {
        phaseHandlerGather = new PhaseHandlerGather();
    }

    @Test
    public void handleCommandDraw() throws Exception {
        Game game = GameFixtures.botGame();
        game.getGamePhases().add(GameFixtures.gamePhase(GamePhaseType.PHASE_GATHER));

        phaseHandlerGather.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_DRAW, ""));

        assertEquals(25, game.getGamePlayers().get(1).getDeck().size());
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
