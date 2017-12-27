package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GamePhase;
import com.eb.server.domain.GamePhaseType;

public class PhaseHandlerGather implements PhaseHandler {
    @Override
    public GamePhase create(Game game) {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setGame(game);
        gamePhase.setGamePhaseType(GamePhaseType.PHASE_GATHER);
        return gamePhase;
    }
}
