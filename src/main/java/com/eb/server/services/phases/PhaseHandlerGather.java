package com.eb.server.services.phases;

import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;

public class PhaseHandlerGather extends AbstractPhaseHandler {
    public PhaseHandlerGather() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_GATHER;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_PLAN;
    }

    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        switch (gameCommand.getType()) {
            case COMMAND_DRAW:
                handleCommandDraw(game, gameCommand);
                return;
            case COMMAND_HARVEST:
                handleCommandHarvest(game, gameCommand);
        }
        super.handleCommand(game, gameCommand);
    }

    void handleCommandDraw(Game game, GameCommand gameCommand) {
        GamePlayer gamePlayer = findGamePlayerCommand(game.getGamePlayers(), gameCommand);
        Integer nCards = Integer.valueOf(gameCommand.getPayload());

        for(int i = 0; i < nCards; i++) {
            GameCard gameCard = gamePlayer.getDeck().remove(0);
            gamePlayer.getHand().add(gameCard);
        }

    }

    void handleCommandHarvest(Game game, GameCommand gameCommand) {
        GamePlayer gamePlayer = findGamePlayerCommand(game.getGamePlayers(), gameCommand);
        gamePlayer.setEnergy(game.getTurn());
    }

    @Override
    public void handleBotCommands(Game game) {
        handleCommandDraw(game, createBotCommand(GameCommandType.COMMAND_DRAW, "5"));
        handleCommandHarvest(game, createBotCommand(GameCommandType.COMMAND_HARVEST, ""));
        // TODO: get energy equivalent to turn
    }
}
