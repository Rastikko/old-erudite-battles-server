package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.types.GamePhaseType;

public interface PhaseHandler {
    void definePhase(Game game);
    void definePhaseAttributes(Game game);
    void handleCommand(Game game, GameCommand gameCommand);
    void handleBotCommands(Game game);
    boolean isNextPhaseReady(Game game);
    GamePhaseType getNextPhaseType(Game game);
}
