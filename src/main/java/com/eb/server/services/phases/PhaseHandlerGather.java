package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCard;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePlayer;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import org.springframework.stereotype.Service;

@Service
public class PhaseHandlerGather extends AbstractPhaseHandler {

    final Integer DEFAULT_DRAW_CARDS = 5;
    final Integer DEFAULT_ENERGY = 5;

    public PhaseHandlerGather() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_GATHER;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_PLAN;
    }

    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) throws Exception {
        switch (gameCommand.getType()) {
            case COMMAND_DRAW:
                handleCommandDraw(game, gameCommand);
            case COMMAND_HARVEST:
                handleCommandHarvest(game, gameCommand);
        }
        super.handleCommand(game, gameCommand);
    }

    void handleCommandDraw(Game game, GameCommand gameCommand) {
        GamePlayer gamePlayer = game.getGamePlayerByUserId(gameCommand.getUserId());
        Integer nCards = DEFAULT_DRAW_CARDS;

        for(int i = 0; i < nCards; i++) {
            GameCard gameCard = gamePlayer.getDeck().remove(0);
            gamePlayer.getHand().add(gameCard);
        }

    }

    void handleCommandHarvest(Game game, GameCommand gameCommand) {
        GamePlayer gamePlayer = game.getGamePlayerByUserId(gameCommand.getUserId());
        gamePlayer.setEnergy(DEFAULT_ENERGY);
    }

    @Override
    public void handleBotCommands(Game game) throws Exception {
        handleCommand(game, createBotCommand(GameCommandType.COMMAND_DRAW, ""));
        handleCommand(game, createBotCommand(GameCommandType.COMMAND_HARVEST, ""));
        handleCommand(game, createBotCommand(GameCommandType.COMMAND_END, ""));
    }
}
