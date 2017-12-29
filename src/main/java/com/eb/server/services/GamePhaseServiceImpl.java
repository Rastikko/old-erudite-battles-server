package com.eb.server.services;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePhaseType;
import com.eb.server.services.phases.PhaseHandler;
import com.eb.server.services.phases.PhaseHandlerGather;
import org.springframework.stereotype.Service;

@Service
public class GamePhaseServiceImpl implements GamePhaseService {

    @Override
    public void handleNewGame(Game game) {
        PhaseHandler phaseHandler = getPhaseHandler(game);
        phaseHandler.definePhase(game);
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        PhaseHandler phaseHandler = getPhaseHandler(game);
        phaseHandler.handleCommand(game, gameCommand);
    }

    PhaseHandler getPhaseHandler(Game game) {
        if (game.getGamePhase() == null) {
            return new PhaseHandlerGather();
        }

        switch (game.getGamePhase().getGamePhaseType()) {
            case PHASE_GATHER:
                return new PhaseHandlerGather();
        }
        return null;
    }
}
