package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import org.springframework.stereotype.Service;

@Service
public class PhaseHandlerBattlePreparation extends AbstractPhaseHandler {

    // TODO: this should be defined by profiles
    public boolean RANDOM_DEFAULT_CATEGORY = true;

    public PhaseHandlerBattlePreparation() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_PREPARATION;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE;

    }

    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleBotCommands(Game game) throws Exception {
        handleCommand(game, createBotCommand(GameCommandType.COMMAND_END, ""));
    }
}
