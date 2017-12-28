package com.eb.server.services;

import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;

public interface GamePhaseService {
    void handleNewGame(Game game);
    void handleCommand(Game game, GameCommand gameCommand);
}
