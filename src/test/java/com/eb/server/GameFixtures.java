package com.eb.server;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.domain.*;
import com.eb.server.domain.types.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class GameFixtures {
    static GameMapper gameMapper = GameMapper.INSTANCE;

    public static Long GAME_ID = 1L;
    public static Long GAME_PHASE_ID = 1L;

    public static Long BOT_ID = 1L;
    public static Long USER_ID = 2L;
    public static Long OTHER_USER_ID = 3L;

    public static Integer BASE_ATTACK = 50;
    public static Integer BASE_HEALTH = 100;

    public static final Long GAME_CARD_ID_1 = 1000L;
    public static final Long GAME_CARD_ID_2 = 2000L;
    public static final Integer GAME_CARD_COST_1 = 1;

    public static final Integer DECK_SIZE = 30;

    public static final AttributeType CARD_ATTRIBUTE_TYPE_1 = AttributeType.ALIGN_LOGIC;
    public static final Integer CARD_ATTRIBUTE_VALUE_1 = 10;

    public static final Calendar START_DATE = new GregorianCalendar(2018, 1, 1);
    public static final Integer START_TURN = 1;

    public static final String QUESTION_TITLE = "What's correct, Answer A or Answer B?";
    public static final QuestionCategoryType QUESTION_CATEGORY = QuestionCategoryType.LOGIC;
    public static final QuestionSubcategoryType QUESTION_SUBCATEGORY = QuestionSubcategoryType.TRIGONOMETRY;
    public static final String QUESTION_ANSWER_1 = "Answer A";
    public static final String QUESTION_ANSWER_2 = "Answer B";
    public static final String QUESTION_CORRECT_ANSWER = "Answer A";

    public static Game botGame() {
        GamePlayer botPlayer = gamePlayer(BOT_ID);
        GamePlayer player = gamePlayer(USER_ID);
        botPlayer.setIsBot(true);

        List<GamePlayer> gamePlayers = new ArrayList<>();
        gamePlayers.add(botPlayer);
        gamePlayers.add(player);

        Game game = new Game();
        game.setId(GAME_ID);
        game.setGamePlayers(gamePlayers);
        game.setTurn(START_TURN);

        return game;
    }

    public static GamePhase gamePhase(GamePhaseType gamePhaseType) {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setId(GAME_PHASE_ID);
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

    public static GamePlayer gamePlayer(Long userId) {
        GamePlayer gamePlayer = new GamePlayer();
        List<GameCard> deck = gameMapper.cardsToGameCards(deck());
        gamePlayer.setId(userId);
        gamePlayer.setUserId(userId);
        gamePlayer.setHealth((int) (userId * BASE_HEALTH));
        gamePlayer.setAttack(BASE_ATTACK);
        gamePlayer.setDeck(deck);
        gamePlayer.setHand(gameCards());
        gamePlayer.setPermanents(gameCards());
        return gamePlayer;
    }

    public static List<Attribute> attributes() {
        Attribute attackAttribute = new Attribute();
        attackAttribute.setType(CARD_ATTRIBUTE_TYPE_1);
        attackAttribute.setValue(CARD_ATTRIBUTE_VALUE_1);

        List<Attribute> attributes = new ArrayList<>();
        attributes.add(attackAttribute);
        return attributes;
    }


    public static List<GameCard> gameCards() {
        List<GameCard> gameCards = new ArrayList<>();
        gameCards.add(gameCard(GAME_CARD_ID_1));
        gameCards.add(gameCard(GAME_CARD_ID_2));
        return gameCards;
    }

    public static GameCard gameCard(Long cardId) {
        GameCard gameCard = new GameCard();
        gameCard.setId(cardId);
        gameCard.setAttributes(attributes());
        return gameCard;
    }

    public static List<Card> deck() {
        List<Card> deck = new ArrayList<>();

        for(int i = 0; i < 30; i++) {
            Card pythagorasTheoremCard = new Card();
            pythagorasTheoremCard.setId(GAME_CARD_ID_1);
            pythagorasTheoremCard.setAttributes(attributes());
            pythagorasTheoremCard.setCost(GAME_CARD_COST_1);
            deck.add(pythagorasTheoremCard);
        }

        return deck;
    }

    public static GameQuestion gameQuestion() {
        Question question = question();

        GameQuestion gameQuestion = new GameQuestion();
        gameQuestion.setTurn(START_TURN);
        gameQuestion.setQuestion(question);
        gameQuestion.setStartDate(START_DATE);
        gameQuestion.setQuestion(question);
        return  gameQuestion;
    }

    public static Question question() {
        List<String> answers = new ArrayList<>();
        answers.add(QUESTION_ANSWER_1);
        answers.add(QUESTION_ANSWER_2);

        Question question = new Question();
        question.setTitle(QUESTION_TITLE);
        question.setCorrectAnswer(QUESTION_CORRECT_ANSWER);
        question.setPotentialAnswers(answers);
        question.setCategory(QUESTION_CATEGORY);
        question.setSubcategory(QUESTION_SUBCATEGORY);
        question.setAverageAnswerTime(20);

        return question;
    }
}
