package com.eb.server.integration;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.bootstrap.Bootstrap;
import com.eb.server.repositories.*;
import com.eb.server.services.*;
import com.eb.server.services.phases.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class AbstractIntegrationTest {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    MatchmakingRequestRepository matchmakingRequestRepository;
    @Autowired
    AttributeRepository attributeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ResourceLoader resourceLoader;

    Bootstrap bootstrap;
    UserService userService;
    GameService gameService;
    GamePhaseService gamePhaseService;
    QuestionService questionService;

    @Before
    @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
    public void setUp() throws Exception {
        bootstrap = new Bootstrap(resourceLoader, userRepository, attributeRepository, cardRepository, questionRepository);
        bootstrap.run();

        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository, bootstrap);
        questionService = new QuestionServiceImpl(questionRepository);
        gamePhaseService = new GamePhaseServiceImpl(
                new PhaseHandlerGather(),
                new PhaseHandlerPlan(),
                new PhaseHandlerBattlePreparation(),
                new PhaseHandlerBattle(questionService),
                new PhaseHandlerBattleResolution(),
                new PhaseHandlerOutcome()
        );

        gameService = new GameServiceImpl(GameMapper.INSTANCE, gameRepository, matchmakingRequestRepository, gamePhaseService, userService);
    }

    GameCommandDTO gameCommandDTO(Long userId, String commandType, String payload) {
        GameCommandDTO command = new GameCommandDTO();
        command.setUserId(userId);
        command.setType(commandType);
        command.setPayload(payload);
        return command;
    }

    UserDTO testCreatePlayer(String name) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        UserDTO savedUserDTO = userService.createNewUser(userDTO);
        // check the default deck is created
        assertEquals(name, savedUserDTO.getName());
        assertEquals(30, savedUserDTO.getDeck().size());
        return savedUserDTO;
    }
}
