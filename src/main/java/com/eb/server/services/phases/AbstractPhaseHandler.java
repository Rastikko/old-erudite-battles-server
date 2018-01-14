package com.eb.server.services.phases;

import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.services.GamePhaseServiceImpl;

import java.util.List;

public abstract class AbstractPhaseHandler implements PhaseHandler {
    GamePhaseType GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;
    GamePhaseType NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;

    // TODO: override and call super to define payload
    public void definePhase(Game game, GamePhaseType gamePhaseType) {
        GamePhase gamePhase = createGamePhase(gamePhaseType);
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
        if (gameCommand.getType().equals(GameCommandType.COMMAND_END)) {
            return true;
        }
        return false;
    }

    GamePhase createGamePhase(GamePhaseType gamePhaseType) {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setGamePhaseType(gamePhaseType);
        return gamePhase;
    }

    GameCommand createBotCommand(GameCommandType commandType, String payload) {
        GameCommand command = new GameCommand();
        command.setType(commandType);
        command.setUserId(Bootstrap.BOT_ID);
        command.setPayload(payload);
        return command;
    }

    GamePlayer findGamePlayerCommand(List<GamePlayer> gamePlayers, GameCommand gameCommand) {
        return gamePlayers.stream()
                .filter(x -> x.getUserId() == gameCommand.getUserId())
                .findFirst()
                .get();
    }

}
