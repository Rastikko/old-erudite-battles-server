package com.eb.server.services;

import com.eb.server.api.v1.model.FindGameDTO;
import com.eb.server.api.v1.model.GameDTO;

public interface GameService {
    GameDTO createNewGame(FindGameDTO findGameDTO);
}
