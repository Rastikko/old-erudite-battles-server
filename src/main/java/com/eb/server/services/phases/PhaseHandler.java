package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GamePhase;

public interface PhaseHandler {
    GamePhase create(Game game);
    void handleBot(Game game);
}
