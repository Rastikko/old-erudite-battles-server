package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GameCard;
import com.eb.server.domain.GameCommand;
import com.eb.server.domain.GamePlayer;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseHandlerPlan extends AbstractPhaseHandler {

    public PhaseHandlerPlan() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_PLAN;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_PREPARATION;

    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        switch (gameCommand.getType()) {
            case COMMAND_PLAY_CARD:
                handleCommandPlayCard(game, gameCommand);
                return;
        }
        super.handleCommand(game, gameCommand);
    }

    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleBotCommands(Game game) {
        handleCommandEnd(game, createBotCommand(GameCommandType.COMMAND_END, ""));
    }

    void handleCommandPlayCard(Game game, GameCommand gameCommand) {
        GamePlayer gamePlayer = findGamePlayerCommand(game.getGamePlayers(), gameCommand);
        Long cardId = Long.valueOf(gameCommand.getPayload());
        GameCard cardToPlay = findCardFromId(cardId, gamePlayer.getHand());
        gamePlayer.getPermanents().add(cardToPlay);
        gamePlayer.getHand().remove(cardToPlay);
        gamePlayer.setEnergy(gamePlayer.getEnergy() - cardToPlay.getCost());
    }

    GameCard findCardFromId(Long id, List<GameCard> cards) {
        return cards
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .get();
    }

}
