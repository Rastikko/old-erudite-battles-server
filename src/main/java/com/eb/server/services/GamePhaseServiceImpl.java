package com.eb.server.services;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.services.phases.PhaseHandler;
import com.eb.server.services.phases.PhaseHandlerGather;
import org.springframework.stereotype.Service;

@Service
public class GamePhaseServiceImpl implements GamePhaseService {

    @Override
    public void handlePhase(Game game) {
        PhaseHandler phaseHandler = new PhaseHandlerGather();
        game.setGamePhase(phaseHandler.create(game));
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {

    }
}
