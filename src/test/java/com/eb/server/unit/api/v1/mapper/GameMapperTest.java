package com.eb.server.unit.api.v1.mapper;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.domain.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameMapperTest {
    public static final Long ID = 1L;
    GameMapper gameMapper = GameMapper.INSTANCE;

    @Test
    public void gameToGameDTO() throws Exception {
        Game game = new Game();
        game.setId(ID);

        GameDTO gameDTO = gameMapper.gameToGameDTO(game);

        assertEquals(ID, gameDTO.getId());

    }
}
