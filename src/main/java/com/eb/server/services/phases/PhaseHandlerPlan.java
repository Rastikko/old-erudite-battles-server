package com.eb.server.services.phases;

import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.services.phases.payloads.PhasePlanPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseHandlerPlan extends AbstractPhaseHandler {

    public PhaseHandlerPlan() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_PLAN;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_PREPARATION;
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) throws Exception {
        switch (gameCommand.getType()) {
            case COMMAND_PLAY_CARD:
                handleCommandPlayCard(game, gameCommand);
        }
        super.handleCommand(game, gameCommand);
    }

    @Override
    public void definePhaseAttributes(Game game) throws Exception{
        // check if is the first plan phase of the turn
        // set the first player to be ready to play a card
        GamePhase previousGamePhase = game.getPreviousGamePhase();
        if (previousGamePhase == null || !previousGamePhase.getType().equals(GamePhaseType.PHASE_PLAN)) {
            String payload = mapper.writeValueAsString(getInitialPlanPayload(game));
            game.getGamePhase().setPayload(payload);
        }
    }

    @Override
    public void handleBotCommands(Game game) throws Exception {
        handleCommand(game, createBotCommand(GameCommandType.COMMAND_END, ""));
    }

    @Override
    void handleCommandEnd(Game game, GameCommand gameCommand) throws Exception {
        PhasePlanPayload payload = mapper.readValue(game.getGamePhase().getPayload(), PhasePlanPayload.class);
        GamePlayer commandGamePlayer = findGamePlayerCommand(game.getGamePlayers(), gameCommand);
        boolean hasPlayerPlayedCard = payload.getPlayedCardId() != 0;
        boolean isCommanderTurnToPlay = commandGamePlayer.getId() == payload.getPlanTurnGamePlayerId();

        if (isCommanderTurnToPlay && !hasPlayerPlayedCard) {
            // the player don't want to keep playing cards
            payload.setSkipPlanTurn(true);
        }

        game.getGamePhase().setPayload(mapper.writeValueAsString(payload));

        super.handleCommandEnd(game, gameCommand);
    }

    PhasePlanPayload getInitialPlanPayload(Game game) {
        PhasePlanPayload payload = new PhasePlanPayload();
        payload.setPlanTurnGamePlayerId(game.getGamePlayers().get(0).getId());
        return payload;
    }

    void handleCommandPlayCard(Game game, GameCommand gameCommand) {
        // TODO: use java predicates to handle any arbitrary attribute
//        GamePlayer gamePlayer = findGamePlayerCommand(game.getGamePlayers(), gameCommand);
//        Long cardId = Long.valueOf(gameCommand.getPayload());
//        GameCard cardToPlay = findCardFromId(cardId, gamePlayer.getHand());
//        gamePlayer.getPermanents().add(cardToPlay);
//        gamePlayer.getHand().remove(cardToPlay);
//        gamePlayer.setEnergy(gamePlayer.getEnergy() - cardToPlay.getCost());
    }

    GameCard findCardFromId(Long id, List<GameCard> cards) {
        return cards
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .get();
    }

}
