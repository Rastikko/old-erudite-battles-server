package com.eb.server.unit.services.phases;

import com.eb.server.GameFixtures;
import com.eb.server.domain.Game;
import com.eb.server.domain.GameCard;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.types.GameCommandType;
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
    public void definePhaseShouldSetPlayerPlayCardIfBot() {
//        Game game = GameFixtures.botGame();
        // TODO
    }

    @Test
    public void playCardCommand() {
        Long CARD_ID = 1L;
        Integer ENERGY_LEFT = 1;
        Game game = GameFixtures.botGame();
        GameCard cardToPlay = game.getGamePlayers().get(1).getDeck().remove(0);
        cardToPlay.setId(CARD_ID);
        game.getGamePlayers().get(1).getHand().add(cardToPlay);
        game.getGamePlayers().get(1).setEnergy(2);
        GameCommand gameCommand = GameFixtures.gameCommand(2L, GameCommandType.COMMAND_PLAY_CARD, cardToPlay.getId().toString());

        phaseHandlerPlan.handleCommand(game, gameCommand);

        assertEquals(1, game.getGamePlayers().get(1).getPermanents().size());
        assertEquals(ENERGY_LEFT, game.getGamePlayers().get(1).getEnergy());
    }
}
