package com.eb.server;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.domain.*;
import com.eb.server.domain.types.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class GameFixtures {
    static GameMapper gameMapper = GameMapper.INSTANCE;

    public static Long USER_ID = 2L;
    public static Long BOT_ID = 1L;

    public static List<Card> deck() {
        List<Card> deck = new ArrayList<>();

        Attribute attackAttribute = new Attribute();
        attackAttribute.setType(AttributeType.ALIGN_LOGIC);
        attackAttribute.setValue(100);

        List<Attribute> attributes = new ArrayList<>();
        attributes.add(attackAttribute);

        Card pythagorasTheoremCard = new Card();
        pythagorasTheoremCard.setId(1L);
        pythagorasTheoremCard.setAttributes(attributes);
        pythagorasTheoremCard.setCost(1);

        for(int i = 0; i < 30; i++) {
            deck.add(pythagorasTheoremCard);
        }

        return deck;
    }

    public static Game game() {
        GamePlayer botPlayer = gamePlayer(BOT_ID);
        GamePlayer player = gamePlayer(2L);
        botPlayer.setIsBot(true);

        List<GamePlayer> gamePlayers = new ArrayList<>();
        gamePlayers.add(botPlayer);
        gamePlayers.add(player);

        Game game = new Game();
        game.setGamePlayers(gamePlayers);
        game.setTurn(1);

        return game;

    }

    public static GamePhase gamePhase(GamePhaseType gamePhaseType) {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setType(gamePhaseType);
        return gamePhase;
    }

    public static GameCommand gameCommand(Long userId, GameCommandType gameCommandType, String payload) {
        GameCommand command = new GameCommand();
        command.setUserId(userId);
        command.setPayload(payload);
        command.setType(gameCommandType);
        return command;
    }

    public static GameQuestion gameQuestion() {
        Question question = new Question();
        question.setTitle("1+1");
        question.setCorrectAnswer("2");
        question.setCategory(QuestionCategoryType.LOGIC);
        question.setSubcategory(QuestionSubcategoryType.TRIGONOMETRY);
        question.setAverageAnswerTime(20);

        GameQuestion gameQuestion = new GameQuestion();
        gameQuestion.setStartDate(new GregorianCalendar(2018, 1, 1));
        gameQuestion.setTurn(1);
        gameQuestion.setQuestion(question);

        return gameQuestion;
    }

    static GamePlayer gamePlayer(Long userId) {
        GamePlayer gamePlayer = new GamePlayer();
        List<GameCard> deck = gameMapper.cardsToGameCards(deck());
        gamePlayer.setUserId(userId);
        gamePlayer.setHealth((int) (userId * 100));
        gamePlayer.setAttack(50);
        gamePlayer.setDeck(deck);
        return gamePlayer;
    }
}
