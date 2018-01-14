package com.eb.server.services;

import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.GameDTO;

public interface GameService {
    GameDTO requestNewGame(RequestGameDTO requestGameDTO);
    GameDTO handleCommand(Long gameId, GameCommandDTO gameCommandDTO);
    GameDTO findGameDTOById(Long gameId);
}
