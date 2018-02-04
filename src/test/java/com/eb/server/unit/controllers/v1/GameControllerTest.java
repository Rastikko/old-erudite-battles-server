package com.eb.server.unit.controllers.v1;

import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.controllers.v1.GameController;
import com.eb.server.domain.types.GameType;
import com.eb.server.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GameControllerTest extends AbstractRestControllerTest {

    public static Long USER_ID = 2L;

    @Mock
    GameService gameService;

    @InjectMocks
    GameController gameController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    public void findGame() throws Exception {
        RequestGameDTO requestGameDTO = new RequestGameDTO();
        requestGameDTO.setUserId(USER_ID);
        requestGameDTO.setType(GameType.VS_BOT);

        when(gameService.requestNewGame(requestGameDTO)).thenReturn(new GameDTO());

        mockMvc.perform(post(GameController.BASE_URL + "/find")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestGameDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void commandGame() throws Exception {
        final Long GAME_ID = 666L;
        GameCommandDTO gameCommandDTO = new GameCommandDTO();
        gameCommandDTO.setType("COMMAND_DRAW");
        gameCommandDTO.setPayload("");

        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(GAME_ID);

        when(gameService.handleCommand(5L, gameCommandDTO)).thenReturn(gameDTO);

        mockMvc.perform(post(GameController.BASE_URL + "/1/command")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gameCommandDTO)))
                .andExpect(status().isCreated());
                // TODO
//                .andExpect(jsonPath("$.id", equalTo(String.valueOf(GAME_ID))));
    }
}
