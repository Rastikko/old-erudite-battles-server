package com.eb.server.unit.services.phases;

import com.eb.server.GameFixtures;
import com.eb.server.domain.Game;
import com.eb.server.domain.GameCard;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePhase;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.services.phases.PhaseHandlerPlan;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhaseHandlerPlanTest {

    PhaseHandlerPlan phaseHandlerPlan;

    @Before
    public void setUp() {
        phaseHandlerPlan = new PhaseHandlerPlan();
    }

    @Test
    public void testMultipleGameRoundsUntilPlayerEnds() throws Exception {
        final String PAYLOAD_1 = "{\"planTurnGamePlayerId\":1,\"playedCardId\":0,\"skipPlanTurn\":true}";

        Game game = GameFixtures.botGame();
        phaseHandlerPlan.definePhase(game);

        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        assertEquals(PAYLOAD_1, game.getGamePhase().getPayload());
    }

    @Test
    public void testMultipleGameRoundsUntilBothPlayersEnd() {
    }
}
