package com.eb.server.services.phases;

import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.*;
import com.eb.server.services.GamePhaseServiceImpl;

public abstract class AbstractPhaseHandler implements PhaseHandler {
    GamePhaseType GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;
    GamePhaseType NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;

    // TODO: override and call super to define payload
    public void definePhase(Game game, GamePhaseType gamePhaseType) {
        GamePhase gamePhase = createGamePhase(game, gamePhaseType);
        game.setGamePhase(gamePhase);

        PhaseHandler phaseHandler = GamePhaseServiceImpl.getPhaseHandler(game);
        phaseHandler.definePhaseAttributes(game);
        phaseHandler.handleBotCommands(game);
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        if (shouldDefineNextPhase(game, gameCommand)) {
            definePhase(game, NEXT_GAME_PHASE_TYPE);
        }
    }

    boolean shouldDefineNextPhase(Game game, GameCommand gameCommand) {
        // TODO: we assume that is always a bot game
        if (gameCommand.getGameCommandType().equals(GameCommandType.COMMAND_END)) {
            return true;
        }
        return false;
    }

    GamePhase createGamePhase(Game game, GamePhaseType gamePhaseType) {
        GamePhase gamePhase = new GamePhase();

        gamePhase.setGame(game);
        gamePhase.setGamePhaseType(gamePhaseType);

        return gamePhase;
    }

    GameCommand createBotCommand(GameCommandType commandType, String payload) {
        GameCommand command = new GameCommand();
        command.setGameCommandType(commandType);
        command.setUserId(Bootstrap.BOT_ID);
        command.setPayload(payload);
        return command;
    }

}
