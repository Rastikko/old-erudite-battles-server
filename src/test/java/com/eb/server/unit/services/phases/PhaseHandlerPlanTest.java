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
    public void testPlayAlignCard() throws Exception {
        Game game = GameFixtures.botGame();
        phaseHandlerPlan.definePhase(game);

        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_END, ""));
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_PLAY_CARD, GameFixtures.payloadPlayCard(1000L)));

        assertEquals(Integer.valueOf(10), game.getGamePlayers().get(1).getGameAlignment().getLogicAlignment());
        assertEquals(1, game.getGamePlayers().get(1).getHand().size());
        assertEquals(1, game.getGamePlayers().get(1).getCemetery().size());

    }

    @Test
    public void testMultipleGameRoundsUntilPlayerEnds() throws Exception {
        final String PAYLOAD_1 = "{\"planTurnGamePlayerId\":1,\"playedCardId\":0,\"skipPlanTurn\":true}";
        final String PAYLOAD_2 = "{\"planTurnGamePlayerId\":2,\"playedCardId\":0,\"skipPlanTurn\":false}";
        final String PAYLOAD_3 = "{\"planTurnGamePlayerId\":2,\"playedCardId\":1000,\"skipPlanTurn\":false}";

        Game game = GameFixtures.botGame();
        phaseHandlerPlan.definePhase(game);

        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        assertEquals(PAYLOAD_1, game.getGamePhase().getPayload());

        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_END, ""));
        assertEquals(GamePhaseType.PHASE_PLAN, phaseHandlerPlan.getNextPhaseType(game));

        phaseHandlerPlan.definePhase(game);
        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        assertEquals(PAYLOAD_2, game.getGamePhase().getPayload());

        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_PLAY_CARD, GameFixtures.payloadPlayCard(1000L)));
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
    public void testMultipleGameRoundsUntilBothPlayersEnd() throws Exception {
        final String PAYLOAD_1 = "{\"planTurnGamePlayerId\":2,\"playedCardId\":0,\"skipPlanTurn\":false}";
        final String PAYLOAD_2 = "{\"planTurnGamePlayerId\":2,\"playedCardId\":1000,\"skipPlanTurn\":false}";
        final String PAYLOAD_3 = "{\"planTurnGamePlayerId\":3,\"playedCardId\":0,\"skipPlanTurn\":false}";
        final String PAYLOAD_4 = "{\"planTurnGamePlayerId\":3,\"playedCardId\":1000,\"skipPlanTurn\":false}";
        final String PAYLOAD_5 = "{\"planTurnGamePlayerId\":2,\"playedCardId\":0,\"skipPlanTurn\":false}";
        final String PAYLOAD_6 = "{\"planTurnGamePlayerId\":2,\"playedCardId\":0,\"skipPlanTurn\":true}";
        final String PAYLOAD_7 = "{\"planTurnGamePlayerId\":3,\"playedCardId\":0,\"skipPlanTurn\":false}";
        final String PAYLOAD_8 = "{\"planTurnGamePlayerId\":3,\"playedCardId\":0,\"skipPlanTurn\":true}";

        Game game = GameFixtures.playerGame();

        phaseHandlerPlan.definePhase(game);

        assertEquals(GamePhaseType.PHASE_PLAN, game.getGamePhase().getType());
        assertEquals(PAYLOAD_1, game.getGamePhase().getPayload());

        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.USER_ID, GameCommandType.COMMAND_PLAY_CARD, GameFixtures.payloadPlayCard(1000L)));
        assertEquals(PAYLOAD_2, game.getGamePhase().getPayload());
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.USER_ID, GameCommandType.COMMAND_END, ""));
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.OTHER_USER_ID, GameCommandType.COMMAND_END, ""));

        phaseHandlerPlan.definePhase(game);
        assertEquals(PAYLOAD_3, game.getGamePhase().getPayload());
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.OTHER_USER_ID, GameCommandType.COMMAND_PLAY_CARD, GameFixtures.payloadPlayCard(1000L)));
        assertEquals(PAYLOAD_4, game.getGamePhase().getPayload());
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.USER_ID, GameCommandType.COMMAND_END, ""));
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.OTHER_USER_ID, GameCommandType.COMMAND_END, ""));

        phaseHandlerPlan.definePhase(game);
        assertEquals(PAYLOAD_5, game.getGamePhase().getPayload());
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.USER_ID, GameCommandType.COMMAND_END, ""));
        assertEquals(PAYLOAD_6, game.getGamePhase().getPayload());
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.OTHER_USER_ID, GameCommandType.COMMAND_END, ""));

        phaseHandlerPlan.definePhase(game);
        assertEquals(PAYLOAD_7, game.getGamePhase().getPayload());
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.USER_ID, GameCommandType.COMMAND_END, ""));
        assertEquals(PAYLOAD_7, game.getGamePhase().getPayload());
        phaseHandlerPlan.handleCommand(game, GameFixtures.gameCommand(GameFixtures.OTHER_USER_ID, GameCommandType.COMMAND_END, ""));
        assertEquals(PAYLOAD_8, game.getGamePhase().getPayload());

        assertEquals(GamePhaseType.PHASE_BATTLE_PREPARATION, phaseHandlerPlan.getNextPhaseType(game));
    }
}
