package com.eb.server.unit.api.v1.mapper;

import com.eb.server.GameFixtures;
import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameMapperTest {
    public static final Long ID = 1L;
    public static final Long GAME_PLAYER_ID_1 = 11L;
    public static final Long GAME_PLAYER_ID_2 = 12L;
    public static final Long GAME_PHASE_ID = 100L;
    public static final Long GAME_CARD_ID_1 = 1000L;
    public static final Long GAME_CARD_ID_2 = 2000L;
    public static final Integer GAME_PLAYER_ATTACK = 50;

    GameMapper gameMapper = GameMapper.INSTANCE;

    @Test
    public void gameToGameDTO() throws Exception {
        Game game = new Game();

        GamePhase gamePhase = new GamePhase();
        gamePhase.setId(GAME_PHASE_ID);

        game.setId(ID);
        game.setGamePlayers(getGamePlayers());
        game.setGamePhase(gamePhase);

        GameDTO gameDTO = gameMapper.gameToGameDTO(game);

        assertEquals(ID, gameDTO.getId());
        assertEquals(GAME_PLAYER_ID_1, gameDTO.getGamePlayers().get(0).getId());
        assertEquals(GAME_PLAYER_ID_2, gameDTO.getGamePlayers().get(1).getId());
        assertEquals(GAME_PHASE_ID, gameDTO.getGamePhase().getId());
        assertEquals(GAME_PLAYER_ATTACK, gameDTO.getGamePlayers().get(0).getAttack());
        assertEquals(Integer.valueOf(2), gameDTO.getGamePlayers().get(0).getDeck());
        assertEquals(2, gameDTO.getGamePlayers().get(0).getHand().size());

        assertEquals(GAME_CARD_ID_1, gameDTO.getGamePlayers().get(0).getPermanents().get(0).getId());
        assertEquals("Answer C", gameDTO.getGamePlayers().get(0).getGameQuestions().get(0).getQuestion().getPotentialAnswers().get(2));
    }

    @Test
    public void cardsToGameCards() throws Exception {
        List<Card> userDeck = GameFixtures.getDefaultDeck();

        List<GameCard> playerDeck = gameMapper.cardsToGameCards(userDeck);

        assertEquals(userDeck.size(), playerDeck.size());
        assertEquals(1, userDeck.get(0).getAttributes().size());
        assertEquals(userDeck.get(0).getAttributes().size(), playerDeck.get(0).getAttributes().size());
    }

    @Test
    public void requestGameCommandDTOToGameCommand() throws Exception {
        final Long USER_ID = 5l;
        final String PAYLOAD = "10";

        GameCommandDTO gameCommandDTO = new GameCommandDTO();
        gameCommandDTO.setPayload(PAYLOAD);
        gameCommandDTO.setType("COMMAND_END");
        gameCommandDTO.setUserId(USER_ID);

        GameCommand gameCommand = gameMapper.requestGameCommandDTOToGameCommand(gameCommandDTO);
        assertEquals(USER_ID, gameCommand.getUserId());
        assertEquals(PAYLOAD, gameCommand.getPayload());
        assertEquals(GameCommandType.COMMAND_END, gameCommand.getType());
    }

    private List<GamePlayer> getGamePlayers() {
        List<GamePlayer> gamePlayers = new ArrayList<>();
        gamePlayers.add(getGamePlayer(1L));
        gamePlayers.add(getGamePlayer(2L));
        return gamePlayers;
    }

    private GamePlayer getGamePlayer(Long userId) {
        GamePlayer gp = new GamePlayer();
        gp.setUserId(userId);
        gp.setDeck(getGameCards());
        gp.setHand(getGameCards());
        gp.setPermanents(getGameCards());
        gp.setAttack(GAME_PLAYER_ATTACK);
        gp.setGameQuestions(new ArrayList<>());
        gp.getGameQuestions().add(getGameQuestion());
        // we make sure the gamePlayer id is different than the userId
        gp.setId(10L + userId);
        return gp;
    }

    private GameQuestion getGameQuestion() {
        GameQuestion gameQuestion = new GameQuestion();
        Question question = new Question();
        List<String> answers = new ArrayList<>();
        answers.add("Answer A");
        answers.add("Answer B");
        answers.add("Answer C");
        answers.add("Answer D");
        question.setCorrectAnswer("Answer A");
        question.setPotentialAnswers(answers);
        gameQuestion.setEndDate(Calendar.getInstance());
        gameQuestion.setStartDate(Calendar.getInstance());
        gameQuestion.setId(1L);
        gameQuestion.setSelectedAnswer("Answer B");
        gameQuestion.setTurn(2);
        gameQuestion.setQuestion(question);
        return  gameQuestion;
    }

    private List<GameCard> getGameCards() {
        List<GameCard> gameCards = new ArrayList<>();
        gameCards.add(getGameCard(GAME_CARD_ID_1));
        gameCards.add(getGameCard(GAME_CARD_ID_2));
        return gameCards;
    }

    private GameCard getGameCard(Long cardId) {
        GameCard gameCard = new GameCard();
        gameCard.setId(cardId);
        return gameCard;
    }
}
