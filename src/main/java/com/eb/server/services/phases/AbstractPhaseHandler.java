package com.eb.server.services.phases;

import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.*;

public abstract class AbstractPhaseHandler implements PhaseHandler {
    GamePhaseType GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;
    GamePhaseType NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;

    // TODO: override and call super to define payload
    public void definePhase(Game game) {
        GamePhase gamePhase = new GamePhase();

        gamePhase.setGame(game);
        gamePhase.setGamePhaseType(GAME_PHASE_TYPE);
        game.setGamePhase(gamePhase);

        handleBotCommands(game);
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        switch (gameCommand.getGameCommandType()) {
            case COMMAND_END:
                handleCommandEnd(game, gameCommand);
                break;

        }
    }

    void handleCommandEnd(Game game, GameCommand gameCommand) {
        // check if is last end required
        // create new phase handler and advance to plan
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
