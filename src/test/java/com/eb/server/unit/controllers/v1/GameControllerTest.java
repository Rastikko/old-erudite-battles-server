package com.eb.server.unit.controllers.v1;

import com.eb.server.api.v1.model.FindGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.controllers.v1.GameController;
import com.eb.server.controllers.v1.UserController;
import com.eb.server.domain.GamePlayer;
import com.eb.server.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

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
        FindGameDTO findGameDTO = new FindGameDTO();
        findGameDTO.setUserId(USER_ID);

        GameDTO returnDTO = getReturnGame();

        when(gameService.createNewGame(findGameDTO)).thenReturn(returnDTO);

        mockMvc.perform(post(GameController.BASE_URL + "/find")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(findGameDTO)))
                .andExpect(status().isCreated());
    }

    private GameDTO getReturnGame() {
        GameDTO gameDTO = new GameDTO();
        return gameDTO;
    }
}
