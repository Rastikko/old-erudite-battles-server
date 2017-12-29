package com.eb.server.services.phases;

import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.*;

public abstract class AbstractPhaseHandler implements PhaseHandler {
    GamePhaseType GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;

    @Override
    public void defineNextPhase(Game game) {
        GamePhase gamePhase = new GamePhase();

        gamePhase.setGame(game);
        gamePhase.setGamePhaseType(GAME_PHASE_TYPE);
        game.setGamePhase(gamePhase);

        handleBotCommands(game);
    }

    GameCommand createBotCommand(GameCommandType commandType, String payload) {
        GameCommand command = new GameCommand();
        command.setGameCommandType(commandType);
        command.setUserId(Bootstrap.BOT_ID);
        command.setPayload(payload);
        return command;
    }

    abstract void handleBotCommands(Game game);

}
