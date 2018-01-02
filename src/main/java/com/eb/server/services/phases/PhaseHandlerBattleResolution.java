package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GamePhaseType;
import com.eb.server.domain.GamePlayer;

public class PhaseHandlerBattleResolution extends AbstractPhaseHandler {
    public PhaseHandlerBattleResolution() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_RESOLUTION;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_GATHER;
    }

    @Override
    public void handleBotCommands(Game game) {

    }

    @Override
    public void definePhaseAttributes(Game game) {
        applyGamePlayerDamage(game.getGamePlayers().get(0), game.getGamePlayers().get(1));
        applyGamePlayerDamage(game.getGamePlayers().get(1), game.getGamePlayers().get(0));

    }

    void applyGamePlayerDamage(GamePlayer gamePlayerAttacker, GamePlayer gamePlayerDefender) {
        Integer health = gamePlayerDefender.getHealth();
        Integer damage = gamePlayerAttacker.getDamage();
        gamePlayerDefender.setHealth(health - damage);
    }
}
