package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePhase;
import com.eb.server.domain.GamePhaseType;

public interface PhaseHandler {
    void definePhase(Game game, GamePhaseType gamePhaseType);
    void definePhaseAttributes(Game game);
    void handleCommand(Game game, GameCommand gameCommand);
    void handleBotCommands(Game game);
}
