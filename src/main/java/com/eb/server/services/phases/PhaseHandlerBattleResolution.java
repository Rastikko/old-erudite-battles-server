package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import com.eb.server.domain.GamePlayer;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import org.springframework.stereotype.Service;

@Service
public class PhaseHandlerBattleResolution extends AbstractPhaseHandler {
    public PhaseHandlerBattleResolution() {
        GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_RESOLUTION;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_GATHER;
    }

    @Override
    public void handleBotCommands(Game game) throws Exception {

        handleCommand(game, createBotCommand(GameCommandType.COMMAND_END, ""));
    }

    @Override
    public void definePhaseAttributes(Game game) {
        applyGamePlayerDamage(game, game.getGamePlayers().get(0), game.getGamePlayers().get(1));
        applyGamePlayerDamage(game, game.getGamePlayers().get(1), game.getGamePlayers().get(0));
        game.setTurn(game.getTurn() + 1);
    }

    @Override
    public GamePhaseType getNextPhaseType(Game game) {
        boolean isNextPhase = isNextPhaseReady(game);
        boolean isGameOver = shouldGameEnd(game);

        if (isNextPhase && isGameOver) {
            return GamePhaseType.PHASE_OUTCOME;
        }
        return NEXT_GAME_PHASE_TYPE;
    }

    boolean shouldGameEnd(Game game) {
        return game.getGamePlayers().stream().anyMatch(player -> player.getHealth() <= 0);
    }

    void applyGamePlayerDamage(Game game, GamePlayer gamePlayerAttacker, GamePlayer gamePlayerDefender) {
        Integer health = gamePlayerDefender.getHealth();
        Integer damage = gamePlayerAttacker.getAttack();
        Integer extraDamage = gamePlayerAttacker.getGameQuestions().stream()
               .filter(question -> question.getTurn().equals(game.getTurn()))
               .filter(question -> question.getPerformance().equals(Integer.valueOf(1)))
               .mapToInt(question -> Integer.valueOf(30))
               .sum();
        gamePlayerDefender.setHealth(health - damage - extraDamage);
    }
}
