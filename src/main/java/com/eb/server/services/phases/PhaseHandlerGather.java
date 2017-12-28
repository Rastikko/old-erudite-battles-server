package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePhaseType;

public class PhaseHandlerGather extends AbstractPhaseHandler {
    public PhaseHandlerGather() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_GATHER;
    }

    @Override
    void handleBotCommands(Game game) {

    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {

    }
}
