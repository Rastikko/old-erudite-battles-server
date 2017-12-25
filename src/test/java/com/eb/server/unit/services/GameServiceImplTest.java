package com.eb.server.unit.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameDTO;
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

public class GameServiceImplTest {

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
        Long USER_ID = 1L;

        when(gameRepository.save(any(Game.class))).thenAnswer(u -> u.getArguments()[0]);

        GameDTO newGameDTO = gameService.createNewGame(USER_ID);

        assertNotNull(newGameDTO);
        // TODO: we should ensure the game is created with 2 game players
        // one pointig to the user, another to the bot
    }
}
