package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.types.GamePhaseType;

public class PhaseHandlerBattlePreparation extends AbstractPhaseHandler {

    public PhaseHandlerBattlePreparation() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_PREPARATION;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE;

    }

    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleBotCommands(Game game) {

    }
}
