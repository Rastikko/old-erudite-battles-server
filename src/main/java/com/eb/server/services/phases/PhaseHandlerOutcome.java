package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.types.GamePhaseType;
import org.springframework.stereotype.Service;

@Service
public class PhaseHandlerOutcome extends AbstractPhaseHandler {

    public PhaseHandlerOutcome() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_OUTCOME;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;

    }

    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleBotCommands(Game game) {

    }
}
