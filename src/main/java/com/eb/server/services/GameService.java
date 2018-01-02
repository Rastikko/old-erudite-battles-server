package com.eb.server.services;

import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.RequestGameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;

public interface GameService {
    GameDTO createNewGame(RequestGameDTO requestGameDTO);
    GameDTO handleCommand(Long gameId, RequestGameCommandDTO requestGameCommandDTO);
    GameDTO findGameDTOById(Long gameId);
}
