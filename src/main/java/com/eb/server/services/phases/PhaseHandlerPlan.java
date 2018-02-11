package com.eb.server.services.phases;

import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.services.phases.payloads.CommandPlayCardPayload;
import com.eb.server.services.phases.payloads.PhasePlanPayload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhaseHandlerPlan extends AbstractPhaseHandler {

    public PhaseHandlerPlan() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_PLAN;
        // TODO: only go to preparation if both players have skip
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_PREPARATION;
    }

    @Override
    public void definePhaseAttributes(Game game) throws Exception {
        // check if is the first plan phase of the turn
        // set the first player to be ready to play a card
        GamePhase previousGamePhase = game.getPreviousGamePhase();
        String payload;
        if (previousGamePhase == null || !previousGamePhase.getType().equals(GamePhaseType.PHASE_PLAN)) {
            payload = mapper.writeValueAsString(getInitialPlanPayload(game));
        } else {
            payload = mapper.writeValueAsString(getNextPayload(game));
        }
        game.getGamePhase().setPayload(payload);
    }

    @Override
    public GamePhaseType getNextPhaseType(Game game) {
        if (getSkippedPlayers(game).size() == 2) {
            return NEXT_GAME_PHASE_TYPE;
        }
        return GAME_PHASE_TYPE;
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
    void handleCommandEnd(Game game, GameCommand gameCommand) throws Exception {
        PhasePlanPayload payload = mapper.readValue(game.getGamePhase().getPayload(), PhasePlanPayload.class);
        GamePlayer commandGamePlayer = findGamePlayerByUserId(game, gameCommand.getUserId());
        boolean hasPlayerPlayedCard = payload.getPlayedCardId() != 0;
        boolean isCommanderTurnToPlay = commandGamePlayer.getId() == payload.getPlanTurnGamePlayerId();

        if (isCommanderTurnToPlay && !hasPlayerPlayedCard) {
            // the player don't want to keep playing cards
            payload.setSkipPlanTurn(true);
        }

        game.getGamePhase().setPayload(mapper.writeValueAsString(payload));
        super.handleCommandEnd(game, gameCommand);
    }

    @Override
    public void handleBotCommands(Game game) throws Exception {
        handleCommand(game, createBotCommand(GameCommandType.COMMAND_END, ""));
    }

    void handleCommandPlayCard(Game game, GameCommand gameCommand) throws Exception{
        PhasePlanPayload payload = mapper.readValue(game.getGamePhase().getPayload(), PhasePlanPayload.class);
        CommandPlayCardPayload commandPayload = mapper.readValue(gameCommand.getPayload(), CommandPlayCardPayload.class);

        payload.setPlayedCardId(commandPayload.getCardId());

        game.getGamePhase().setPayload(mapper.writeValueAsString(payload));
    }

    PhasePlanPayload getInitialPlanPayload(Game game) {
        PhasePlanPayload payload = new PhasePlanPayload();
        payload.setPlanTurnGamePlayerId(game.getGamePlayers().get(0).getId());
        return payload;
    }

    PhasePlanPayload getNextPayload(Game game) throws Exception {
        // if there are no skips then get the other player from previous turn
        Long skippedPlayer = getSkippedPlayers(game).stream().findFirst().get();
        PhasePlanPayload payload = new PhasePlanPayload();
        if (skippedPlayer != null) {
            payload.setPlanTurnGamePlayerId(findOtherGamePlayerByGamePlayerId(game, skippedPlayer).getId());
        } else {
            PhasePlanPayload previousPayload = mapper.readValue(game.getPreviousGamePhase().getPayload(), PhasePlanPayload.class);
            payload.setPlanTurnGamePlayerId(findOtherGamePlayerByGamePlayerId(game, previousPayload.getPlanTurnGamePlayerId()).getId());
        }
        // if there is a skip return the other player
        return payload;
    }

    List<Long> getSkippedPlayers(Game game) {
        return game.getGamePhases()
                .stream()
                .map(gamePhase -> {
                    try {
                        PhasePlanPayload payload = mapper.readValue(gamePhase.getPayload(), PhasePlanPayload.class);
                        if (payload.getSkipPlanTurn()) {
                            return payload.getPlanTurnGamePlayerId();
                        }
                    } catch (Exception e) {}
                    return null;
                })
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
    }

}
