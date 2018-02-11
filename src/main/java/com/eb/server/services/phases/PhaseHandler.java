package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.types.GamePhaseType;

public interface PhaseHandler {
    void definePhase(Game game) throws Exception;
    void handleCommand(Game game, GameCommand gameCommand) throws  Exception;
    boolean isNextPhaseReady(Game game);
    GamePhaseType getNextPhaseType(Game game);
}
