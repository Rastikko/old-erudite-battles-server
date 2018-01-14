package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import org.springframework.stereotype.Service;

@Service
public class PhaseHandlerBattle extends AbstractPhaseHandler {

    public PhaseHandlerBattle() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_RESOLUTION;

    }

    @Override
    public void definePhaseAttributes(Game game) {
        // we need to define the first GameQuestion of the turn
        // for that we should have available the help of QuestionService
    }

    @Override
    public void handleBotCommands(Game game) {
        handleCommandEnd(game, createBotCommand(GameCommandType.COMMAND_END, ""));

    }
}
