package com.eb.server.unit.api.v1.mapper;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.domain.Game;
import com.eb.server.domain.GamePhase;
import com.eb.server.domain.GamePlayer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameMapperTest {
    public static final Long ID = 1L;
    public static final Long GAME_PLAYER_ID_1 = 11L;
    public static final Long GAME_PLAYER_ID_2 = 12L;
    public static final Long GAME_PHASE_ID = 100L;

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
    }

    private GamePhase getGamePhase(Game game) {
        GamePhase gamePhase = new GamePhase();
        gamePhase.setId(GAME_PHASE_ID);
        gamePhase.setGame(game);
        return gamePhase;
    }

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
        // we make sure the gamePlayer id is different than the userId
        gp.setId(10L + userId);
        return gp;
    }
}
