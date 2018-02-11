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
        final String PAYLOAD_2 = "{\"planTurnGamePlayerId\":2,\"playedCardId\":0,\"skipPlanTurn\":false}";
        final String PAYLOAD_3 = "{\"planTurnGamePlayerId\":2,\"playedCardId\":1,\"skipPlanTurn\":false}";

        Game game = GameFixtures.botGame();
        phaseHandlerPlan.definePhase(game);

        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        assertEquals(PAYLOAD_1, game.getGamePhase().getPayload());

        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_END, ""));
        assertEquals(GamePhaseType.PHASE_PLAN, phaseHandlerPlan.getNextPhaseType(game));

        phaseHandlerPlan.definePhase(game);
        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        assertEquals(PAYLOAD_2, game.getGamePhase().getPayload());

        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_PLAY_CARD, "{\"cardId\":1}"));
        assertEquals(PAYLOAD_3, game.getGamePhase().getPayload());

        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_END, ""));
        assertEquals(GamePhaseType.PHASE_PLAN, phaseHandlerPlan.getNextPhaseType(game));

        phaseHandlerPlan.definePhase(game);
        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        assertEquals(PAYLOAD_2, game.getGamePhase().getPayload());

        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_END, ""));
        assertEquals(GamePhaseType.PHASE_BATTLE_PREPARATION, phaseHandlerPlan.getNextPhaseType(game));
    }

    @Test
    public void testMultipleGameRoundsUntilBothPlayersEnd() {
    }
}
