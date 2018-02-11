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
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameMapperTest {
    public static final Long ID = 1L;
    public static final Long GAME_PLAYER_ID_1 = 1L;
    public static final Long GAME_PLAYER_ID_2 = 2L;
    public static final Long GAME_PHASE_ID = 100L;
    public static final Long GAME_CARD_ID_1 = 1000L;
    public static final Integer GAME_PLAYER_ATTACK = 50;
    public static final Integer DECK_SIZE = 30;
    public static final int HAND_SIZE = 2;
    public static final String POTENTIAL_ANSWER_2 = "Answer C";

    GameMapper gameMapper = GameMapper.INSTANCE;

    @Test
    public void gameToGameDTO() {
        Game game = new Game();

        GamePhase gamePhase = new GamePhase();
        gamePhase.setId(GAME_PHASE_ID);

        game.setId(ID);
        game.setGamePlayers(getGamePlayers());
        game.getGamePhases().add(gamePhase);

        GameDTO gameDTO = gameMapper.gameToGameDTO(game);

        assertEquals(ID, gameDTO.getId());
        assertEquals(GAME_PLAYER_ID_1, gameDTO.getGamePlayers().get(0).getId());
        assertEquals(GAME_PLAYER_ID_2, gameDTO.getGamePlayers().get(1).getId());
        assertEquals(GAME_PHASE_ID, gameDTO.getGamePhase().getId());
        assertEquals(GAME_PLAYER_ATTACK, gameDTO.getGamePlayers().get(0).getAttack());
        assertEquals(DECK_SIZE, gameDTO.getGamePlayers().get(0).getDeck());
        assertEquals(HAND_SIZE, gameDTO.getGamePlayers().get(0).getHand().size());

        assertEquals(GAME_CARD_ID_1, gameDTO.getGamePlayers().get(0).getPermanents().get(0).getId());
        assertEquals(POTENTIAL_ANSWER_2, gameDTO.getGamePlayers().get(0).getGameQuestions().get(0).getQuestion().getPotentialAnswers().get(2));
    }

    @Test
    public void cardsToGameCards() {
        List<Card> userDeck = GameFixtures.deck();

        List<GameCard> playerDeck = gameMapper.cardsToGameCards(userDeck);

        assertEquals(userDeck.size(), playerDeck.size());
        assertEquals(1, userDeck.get(0).getAttributes().size());
        assertEquals(userDeck.get(0).getAttributes().size(), playerDeck.get(0).getAttributes().size());
    }

    @Test
    public void requestGameCommandDTOToGameCommand() {
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
        gamePlayers.add(GameFixtures.gamePlayer(1L));
        gamePlayers.add(GameFixtures.gamePlayer(2L));
        gamePlayers.get(0).getGameQuestions().add(GameFixtures.gameQuestion());
        gamePlayers.get(1).getGameQuestions().add(GameFixtures.gameQuestion());
        return gamePlayers;
    }
}
