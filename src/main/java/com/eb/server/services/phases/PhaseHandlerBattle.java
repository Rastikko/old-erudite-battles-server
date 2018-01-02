package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.types.GamePhaseType;

public class PhaseHandlerBattle extends AbstractPhaseHandler {

    public PhaseHandlerBattle() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_RESOLUTION;

    }

    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleBotCommands(Game game) {

    }
}
