package com.eb.server;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.*;

import java.util.ArrayList;
import java.util.List;

public class GameFixtures {
    static GameMapper gameMapper = GameMapper.INSTANCE;

    public static List<Card> getDefaultDeck() {
        // TODO: decouple from implementation
        return Bootstrap.getDefaultDeck();
    }

    public static Game game() {
        Game game = new Game();
        List<GamePlayer> gamePlayers = new ArrayList<>();

        gamePlayers.add(geMockedGamePlayer(Bootstrap.BOT_ID));
        gamePlayers.add(geMockedGamePlayer(2L));
        game.setGamePlayers(gamePlayers);

        return game;

    }

    public static GamePhase gamePhase(GamePhaseType gamePhaseType) {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setGamePhaseType(gamePhaseType);
        return gamePhase;
    }

    public static GameCommand drawCommand(String payload) {
        GameCommand command = new GameCommand();
        command.setUserId(Bootstrap.BOT_ID);
        command.setPayload(payload);
        command.setGameCommandType(GameCommandType.COMMAND_DRAW);
        return command;
    }

    public static GameCommand endCommand() {
        GameCommand command = new GameCommand();
        command.setUserId(Bootstrap.BOT_ID);
        command.setGameCommandType(GameCommandType.COMMAND_END);
        return command;
    }

    static GamePlayer geMockedGamePlayer(Long userId) {
        GamePlayer gamePlayer = new GamePlayer();
        List<GameCard> deck = gameMapper.cardsToGameCards(getDefaultDeck());
        List<GameCard> hand = new ArrayList<>();
        gamePlayer.setUserId(userId);
        gamePlayer.setDeck(deck);
        gamePlayer.setHand(hand);
        return gamePlayer;
    }
}
