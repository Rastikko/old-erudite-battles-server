package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GamePhase;
import com.eb.server.domain.GamePhaseType;

public abstract class AbstractPhaseHandler implements PhaseHandler {
    GamePhaseType GAME_PHASE_TYPE = GamePhaseType.PHASE_NONE;

    @Override
    public void defineNextPhase(Game game) {
        GamePhase gamePhase = new GamePhase();

        gamePhase.setGame(game);
        gamePhase.setGamePhaseType(GAME_PHASE_TYPE);
        game.setGamePhase(gamePhase);

        // TODO: ensure is a bot game first
        handleBotCommands(game);
    }

    abstract void handleBotCommands(Game game);

}
