package com.eb.server.services.phases;

import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;

import java.util.List;

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
        switch (gameCommand.getGameCommandType()) {
            case COMMAND_DRAW:
                handleCommandDraw(game, gameCommand);
                return;
            case COMMAND_HARVEST:
                handlecommandHarvest(game, gameCommand);
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

    void handlecommandHarvest(Game game, GameCommand gameCommand) {
        GamePlayer gamePlayer = findGamePlayerCommand(game.getGamePlayers(), gameCommand);
        gamePlayer.setEnergy(game.getTurn());
    }

    @Override
    public void handleBotCommands(Game game) {
        handleCommandDraw(game, createBotCommand(GameCommandType.COMMAND_DRAW, "5"));
        // TODO: get energy equivalent to turn
    }
}
