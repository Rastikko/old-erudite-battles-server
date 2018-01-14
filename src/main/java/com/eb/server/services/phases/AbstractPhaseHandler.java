package com.eb.server.services.phases;

import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.services.GamePhaseService;
import com.eb.server.services.GamePhaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

public abstract class AbstractPhaseHandler implements PhaseHandler {
    GamePhaseType GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;
    GamePhaseType NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;

    // TODO: override and call super to define payload
    public void definePhase(Game game) {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setType(this.GAME_PHASE_TYPE);

        game.setGamePhase(gamePhase);

        definePhaseAttributes(game);
        handleBotCommands(game);
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        if (gameCommand.getType().equals(GameCommandType.COMMAND_END)) {
            this.handleCommandEnd(game, gameCommand);
        }
    }

    @Override
    public boolean isNextPhaseReady(Game game) {
        if (game.getGamePhase().getEndPhaseGamePlayerIds().size() == 2) {
            return true;
        }
        return false;
    }

    @Override
    public GamePhaseType getNextPhaseType(Game game) {
        return NEXT_GAME_PHASE_TYPE;
    }

    void handleCommandEnd(Game game, GameCommand gameCommand) {
        game.getGamePhase().getEndPhaseGamePlayerIds().add(gameCommand.getUserId());
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
