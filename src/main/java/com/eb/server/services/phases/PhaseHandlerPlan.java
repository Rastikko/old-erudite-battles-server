package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePhaseType;

public class PhaseHandlerPlan extends AbstractPhaseHandler {
    @Override
    void handleBotCommands(Game game) {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_PLAN;
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {

    }
}
