package com.eb.server.unit.api.v1.mapper;

import com.eb.server.GameFixtures;
import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameMapperTest {
    public static final int HAND_SIZE = 2;

    GameMapper gameMapper = GameMapper.INSTANCE;

    @Test
    public void gameToGameDTO() {
        Game game = GameFixtures.botGame();
        game.getGamePhases().add(GameFixtures.gamePhase(GamePhaseType.PHASE_NONE));
        game.getGamePlayers().get(0).getGameQuestions().add(GameFixtures.gameQuestion());

        GameDTO gameDTO = gameMapper.gameToGameDTO(game);

        assertEquals(GameFixtures.GAME_ID, gameDTO.getId());
        assertEquals(GameFixtures.BOT_ID, gameDTO.getGamePlayers().get(0).getId());
        assertEquals(GameFixtures.USER_ID, gameDTO.getGamePlayers().get(1).getId());
        assertEquals(GameFixtures.GAME_PHASE_ID, gameDTO.getGamePhase().getId());
        assertEquals(GameFixtures.BASE_ATTACK, gameDTO.getGamePlayers().get(0).getAttack());
        assertEquals(GameFixtures.DECK_SIZE, gameDTO.getGamePlayers().get(0).getDeck());
        assertEquals(GameFixtures.HAND_SIZE, gameDTO.getGamePlayers().get(0).getHand().size());

        assertEquals(GameFixtures.GAME_CARD_ID_1, gameDTO.getGamePlayers().get(0).getPermanents().get(0).getId());
        assertEquals(GameFixtures.QUESTION_ANSWER_2, gameDTO.getGamePlayers().get(0).getGameQuestions().get(0).getQuestion().getPotentialAnswers().get(1));
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
}
