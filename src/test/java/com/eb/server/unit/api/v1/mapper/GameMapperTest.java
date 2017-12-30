package com.eb.server.unit.api.v1.mapper;

import com.eb.server.GameFixtures;
import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.RequestGameCommandDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameMapperTest {
    public static final Long ID = 1L;
    public static final Long GAME_PLAYER_ID_1 = 11L;
    public static final Long GAME_PLAYER_ID_2 = 12L;
    public static final Long GAME_PHASE_ID = 100L;
    public static final Long GAME_CARD_ID_1 = 1000L;
    public static final Long GAME_CARD_ID_2 = 2000L;

    GameMapper gameMapper = GameMapper.INSTANCE;

    @Test
    public void gameToGameDTO() throws Exception {
        Game game = new Game();

        game.setId(ID);
        game.setGamePlayers(getGamePlayers(game));
        game.setGamePhase(getGamePhase(game));

        GameDTO gameDTO = gameMapper.gameToGameDTO(game);

        assertEquals(ID, gameDTO.getId());
        assertEquals(gameDTO.getGamePlayers().get(0).getId(), GAME_PLAYER_ID_1);
        assertEquals(gameDTO.getGamePlayers().get(1).getId(), GAME_PLAYER_ID_2);
        assertEquals(gameDTO.getGamePhase().getId(), GAME_PHASE_ID);
        assertEquals(gameDTO.getGamePlayers().get(0).getDeck().size(), 2);
        assertEquals(gameDTO.getGamePlayers().get(0).getHand().size(), 2);
        assertEquals(gameDTO.getGamePlayers().get(0).getDeck().get(0).getId(), GAME_CARD_ID_1);
    }

    @Test
    public void cardsToGameCards() throws Exception {
        List<Card> userDeck = GameFixtures.getDefaultDeck();

        List<GameCard> playerDeck = gameMapper.cardsToGameCards(userDeck);

        assertEquals(userDeck.size(), playerDeck.size());
        assertEquals(userDeck.get(0).getId(), playerDeck.get(0).getCardId());
    }

    @Test
    public void requestGameCommandDTOToGameCommand() throws Exception {
        final Long USER_ID = 5l;
        final String PAYLOAD = "10";

        RequestGameCommandDTO requestGameCommandDTO = new RequestGameCommandDTO();
        requestGameCommandDTO.setPayload(PAYLOAD);
        requestGameCommandDTO.setGameCommandType("COMMAND_END");
        requestGameCommandDTO.setUserId(USER_ID);

        GameCommand gameCommand = gameMapper.requestGameCommandDTOToGameCommand(requestGameCommandDTO);
        assertEquals(USER_ID, gameCommand.getUserId());
        assertEquals(PAYLOAD, gameCommand.getPayload());
        assertEquals(GameCommandType.COMMAND_END, gameCommand.getGameCommandType());
    }

    // TODO: move these to game fixtures
    private GamePhase getGamePhase(Game game) {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setId(GAME_PHASE_ID);
        gamePhase.setGame(game);
        return gamePhase;
    }

    // TODO: investigate if we need to pass game
    private List<GamePlayer> getGamePlayers(Game game) {
        List<GamePlayer> gamePlayers = new ArrayList<>();
        gamePlayers.add(getGamePlayer(1L, game));
        gamePlayers.add(getGamePlayer(2L, game));
        return gamePlayers;
    }

    private GamePlayer getGamePlayer(Long userId, Game game) {
        GamePlayer gp = new GamePlayer();
        gp.setUserId(userId);
        gp.setGame(game);
        gp.setDeck(getGameCards());
        gp.setHand(getGameCards());
        // we make sure the gamePlayer id is different than the userId
        gp.setId(10L + userId);
        return gp;
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
