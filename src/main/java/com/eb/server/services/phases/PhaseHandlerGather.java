package com.eb.server.services.phases;

import com.eb.server.domain.*;

import java.util.List;

public class PhaseHandlerGather extends AbstractPhaseHandler {
    public PhaseHandlerGather() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_GATHER;
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        switch (gameCommand.getGameCommandType()) {
            case COMMAND_DRAW:
                handleDrawCommand(game, gameCommand);
        }
    }

    void handleDrawCommand(Game game, GameCommand gameCommand) {
        GamePlayer gamePlayer = findGamePlayerCommand(game.getGamePlayers(), gameCommand);
        Integer nCards = Integer.valueOf(gameCommand.getPayload());

        for(int i = 0; i < nCards; i++) {
            GameCard gameCard = gamePlayer.getDeck().remove(0);
            gamePlayer.getHand().add(gameCard);
        }

    }

    @Override
    void handleBotCommands(Game game) {
        handleDrawCommand(game, createBotCommand(GameCommandType.COMMAND_DRAW, "5"));
    }

    GamePlayer findGamePlayerCommand(List<GamePlayer> gamePlayers, GameCommand gameCommand) {
        return gamePlayers.stream()
                .filter(x -> x.getUserId() == gameCommand.getUserId())
                .findFirst()
                .get();
    }
}
