package com.eb.server.unit.services.phases;

import com.eb.server.GameFixtures;
import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.services.phases.AbstractPhaseHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractPhaseHandlerTest {

    static class GenericPhaseHandler extends  AbstractPhaseHandler {
        @Override
        public void handleBotCommands(Game game) {}

        @Override
        protected void definePhaseAttributes(Game game) {}
    }

    AbstractPhaseHandler phaseHandler;

    @Before
    public void setUp() {
        phaseHandler = new GenericPhaseHandler();
    }

    @Test
    public void shouldSaveHandledCommands() {
        Game game = GameFixtures.botGame();
        phaseHandler.definePhase(game);
        GameCommand gameCommand = GameFixtures.gameCommand(GameFixtures.USER_ID, GameCommandType.COMMAND_END, "");
        phaseHandler.handleCommand(game, gameCommand);
        assertEquals(1, game.getGamePhase().getGameCommands().size());
    }
}
