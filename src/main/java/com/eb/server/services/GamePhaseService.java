package com.eb.server.services;

import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;

public interface GamePhaseService {
    void handlePhase(Game game);
    void handleCommand(Game game, GameCommand gameCommand);
    // define next phase
        // each phase will have it's own implementation interface
        // a phase might have it's own DTO for the payload, like example resolution have stat information
        // execute bot actions
    // execute a command phase
        // each command will have it's own class
        // a command will have different request DTO, DRAW vs PLAY(x)
    // return always a decorated game?
}
