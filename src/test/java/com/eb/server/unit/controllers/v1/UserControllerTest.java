package com.eb.server.unit.controllers.v1;

import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.controllers.v1.UserController;
import com.eb.server.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractRestControllerTest {

    public static final String NAME = "Johan";

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void testGetUserById() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName(NAME);

        when(userService.findUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get(UserController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void createNewUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setName(NAME);

        UserDTO returnDTO = new UserDTO();
        returnDTO.setName(user.getName());
        returnDTO.setId(2L);

        when(userService.createNewUser(user)).thenReturn(returnDTO);

        mockMvc.perform(post(UserController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.id", equalTo(2)));

    }
}
