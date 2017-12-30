package com.eb.server.unit.controllers.v1;

import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.RequestGameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.controllers.v1.GameController;
import com.eb.server.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        when(gameService.createNewGame(requestGameDTO)).thenReturn(new GameDTO());

        mockMvc.perform(post(GameController.BASE_URL + "/find")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestGameDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void commandGame() throws Exception {
        final Long GAME_ID = 666L;
        RequestGameCommandDTO requestGameCommandDTO = new RequestGameCommandDTO();
        requestGameCommandDTO.setGameCommandType("COMMAND_DRAW");
        requestGameCommandDTO.setPayload("5");

        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(GAME_ID);

        when(gameService.handleCommand(5L, requestGameCommandDTO)).thenReturn(gameDTO);

        mockMvc.perform(post(GameController.BASE_URL + "/find")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestGameCommandDTO)))
                .andExpect(status().isCreated());
                // TODO
//                .andExpect(jsonPath("$.id", equalTo(String.valueOf(GAME_ID))));
    }
}
