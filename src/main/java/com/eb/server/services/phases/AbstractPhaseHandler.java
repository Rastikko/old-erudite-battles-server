package com.eb.server.services.phases;

import com.eb.server.bootstrap.Bootstrap;
import com.eb.server.domain.Game;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePhase;
import com.eb.server.domain.GamePlayer;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.domain.types.GameType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.GregorianCalendar;
import java.util.List;

public abstract class AbstractPhaseHandler implements PhaseHandler {
    GamePhaseType GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;
    GamePhaseType NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;

    ObjectMapper mapper = new ObjectMapper();

    protected abstract void definePhaseAttributes(Game game) throws Exception;
    protected abstract void handleBotCommands(Game game) throws Exception;

    public void definePhase(Game game) throws Exception {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setType(this.GAME_PHASE_TYPE);
        game.getGamePhases().add(gamePhase);

        definePhaseAttributes(game);

        if (game.getGameType().equals(GameType.VS_BOT)) {
            handleBotCommands(game);
        }
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) throws  Exception{
        if (gameCommand.getType().equals(GameCommandType.COMMAND_END)) {
            this.handleCommandEnd(game, gameCommand);
        }
        gameCommand.setDate(new GregorianCalendar());
        game.getGamePhase().getGameCommands().add(gameCommand);
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

    void handleCommandEnd(Game game, GameCommand gameCommand) throws Exception {
        game.getGamePhase().getEndPhaseGamePlayerIds().add(gameCommand.getUserId());
    }

    GameCommand createBotCommand(GameCommandType commandType, String payload) {
        GameCommand command = new GameCommand();
        command.setType(commandType);
        command.setUserId(Bootstrap.BOT_ID);
        command.setPayload(payload);
        return command;
    }

    GamePlayer findGamePlayerByUserId(Game game, Long userId) {
        return game.getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getUserId() == userId)
                .findFirst()
                .get();
    }

    GamePlayer findOtherGamePlayerByUserId(Game game, Long userId) {
        return game.getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getUserId() != userId)
                .findFirst()
                .get();
    }

    GamePlayer findOtherGamePlayerByGamePlayerId(Game game, Long gamePlayerId) {
        return game.getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getId() != gamePlayerId)
                .findFirst()
                .get();
    }
}
