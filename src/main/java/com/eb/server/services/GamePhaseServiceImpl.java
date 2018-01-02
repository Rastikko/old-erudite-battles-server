package com.eb.server.services;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.services.phases.*;
import org.springframework.stereotype.Service;

@Service
public class GamePhaseServiceImpl implements GamePhaseService {

    @Override
    public void handleNewGame(Game game) {
        PhaseHandler phaseHandler = getPhaseHandler(game);
        phaseHandler.definePhase(game, GamePhaseType.PHASE_GATHER);
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        PhaseHandler phaseHandler = getPhaseHandler(game);
        phaseHandler.handleCommand(game, gameCommand);
    }

    public static PhaseHandler getPhaseHandler(Game game) {
        if (game.getGamePhase() == null) {
            return new PhaseHandlerGather();
        }

        switch (game.getGamePhase().getGamePhaseType()) {
            case PHASE_GATHER:
                return new PhaseHandlerGather();
            case PHASE_PLAN:
                return new PhaseHandlerPlan();
            case PHASE_BATTLE_PREPARATION:
                return new PhaseHandlerBattlePreparation();
            case PHASE_BATTLE:
                return new PhaseHandlerBattle();
            case PHASE_BATTLE_RESOLUTION:
                return new PhaseHandlerBattleResolution();
            case PHASE_OUTCOME:
                return new PhaseHandlerOutcome();
            case PHASE_NONE:
                return new PhaseHandlerNone();
        }
        return null;
    }
}
