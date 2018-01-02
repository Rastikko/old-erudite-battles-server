package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePhaseType;

public class PhaseHandlerPlan extends AbstractPhaseHandler {

    public PhaseHandlerPlan() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_PLAN;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_PREPARATION;

    }

    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleBotCommands(Game game) {
    }

}
