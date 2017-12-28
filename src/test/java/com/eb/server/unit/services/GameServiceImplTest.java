package com.eb.server.unit.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.FindGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.GamePlayerDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.Game;
import com.eb.server.domain.GamePlayer;
import com.eb.server.domain.User;
import com.eb.server.repositories.GameRepository;
import com.eb.server.services.GamePhaseService;
import com.eb.server.services.GameService;
import com.eb.server.services.GameServiceImpl;
import com.eb.server.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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

    @Mock
    GamePhaseService gamePhaseService;

    @Mock
    UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(userService.findUserByID(any(Long.class))).thenAnswer(u -> {
            User user = new User();
            user.setId((Long) u.getArguments()[0]);
            return user;
        });

        gameService = new GameServiceImpl(gameMapper, gameRepository, gamePhaseService, userService);
    }

    @Test
    public void createNewGameVsBot() {
        // this is not 100% unit but meh
        when(gameRepository.save(any(Game.class))).thenAnswer(u -> u.getArguments()[0]);

        FindGameDTO findGameDTO = new FindGameDTO();
        findGameDTO.setUserId(USER_ID);

        GameDTO newGameDTO = gameService.createNewGame(findGameDTO);

        assertNotNull(newGameDTO);
        assertEquals(2, newGameDTO.getGamePlayers().size());

        assertThat( newGameDTO.getGamePlayers(), contains(
                hasProperty("userId", is(Bootstrap.BOT_ID)),
                hasProperty("userId", is(USER_ID))
        ));
    }
}
