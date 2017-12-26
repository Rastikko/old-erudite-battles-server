package com.eb.server.unit.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.Game;
import com.eb.server.repositories.GameRepository;
import com.eb.server.services.GameService;
import com.eb.server.services.GameServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;


public class GameServiceImplTest {

    public static Long USER_ID = 2L;

    GameService gameService;
    GameMapper gameMapper = GameMapper.INSTANCE;

    @Mock
    GameRepository gameRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gameService = new GameServiceImpl(gameMapper, gameRepository);
    }

    @Test
    public void createNewGameVsBot() {
        when(gameRepository.save(any(Game.class))).thenAnswer(u -> u.getArguments()[0]);

        GameDTO newGameDTO = gameService.createNewGame(USER_ID);

        assertNotNull(newGameDTO);
        assertEquals(2, newGameDTO.getGamePlayers().size());

        assertThat( newGameDTO.getGamePlayers(), contains(
                hasProperty("userId", is(Bootstrap.BOT_ID)),
                hasProperty("userId", is(USER_ID))
        ));
    }
}
