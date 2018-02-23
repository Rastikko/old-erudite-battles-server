package com.eb.server.services;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.services.phases.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GamePhaseServiceImpl implements GamePhaseService {

    private static final Logger logger = LogManager.getLogger(GamePhaseServiceImpl.class);

    PhaseHandlerGather phaseHandlerGather;
    PhaseHandlerPlan phaseHandlerPlan;
    PhaseHandlerBattlePreparation phaseHandlerBattlePreparation;
    PhaseHandlerBattle phaseHandlerBattle;
    PhaseHandlerBattleResolution phaseHandlerBattleResolution;
    PhaseHandlerOutcome phaseHandlerOutcome;

    public GamePhaseServiceImpl(
        PhaseHandlerGather phaseHandlerGather,
        PhaseHandlerPlan phaseHandlerPlan,
        PhaseHandlerBattlePreparation phaseHandlerBattlePreparation,
        PhaseHandlerBattle phaseHandlerBattle,
        PhaseHandlerBattleResolution phaseHandlerBattleResolution,
        PhaseHandlerOutcome phaseHandlerOutcome
    ) {
        this.phaseHandlerGather = phaseHandlerGather;
        this.phaseHandlerPlan = phaseHandlerPlan;
        this.phaseHandlerBattlePreparation = phaseHandlerBattlePreparation;
        this.phaseHandlerBattle = phaseHandlerBattle;
        this.phaseHandlerBattleResolution = phaseHandlerBattleResolution;
        this.phaseHandlerOutcome = phaseHandlerOutcome;
    }

    @Override
    public void handleNewGame(Game game) {
        try {
            PhaseHandler phaseHandler = getPhaseHandler(GamePhaseType.PHASE_GATHER);
            phaseHandler.definePhase(game);
        } catch (Exception e) {
            logger.error("handleNewGame: " + e);
        }
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        try {
            PhaseHandler phaseHandler = getPhaseHandler(game.getGamePhase().getType());
            phaseHandler.handleCommand(game, gameCommand);
            if (phaseHandler.isNextPhaseReady(game)) {
                PhaseHandler newPhaseHandler = getPhaseHandler(phaseHandler.getNextPhaseType(game));
                newPhaseHandler.definePhase(game);
            }
        } catch (Exception e) {
            logger.error("handleCommand: " + e);
        }
    }

    private PhaseHandler getPhaseHandler(GamePhaseType gamePhaseType) {
        switch (gamePhaseType) {
            case PHASE_GATHER:
                return this.phaseHandlerGather;
            case PHASE_PLAN:
                return this.phaseHandlerPlan;
            case PHASE_BATTLE_PREPARATION:
                return this.phaseHandlerBattlePreparation;
            case PHASE_BATTLE:
                return this.phaseHandlerBattle;
            case PHASE_BATTLE_RESOLUTION:
                return this.phaseHandlerBattleResolution;
            case PHASE_OUTCOME:
                return this.phaseHandlerOutcome;
        }
        return null;
    }
}
