package com.eb.server.integration;

import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.User;
import com.eb.server.repositories.CardRepository;
import com.eb.server.repositories.UserRepository;
import com.eb.server.services.UserService;
import com.eb.server.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BoostrapIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    UserService userService;

    @Before
    public void setUp() throws Exception {

        Bootstrap bootstrap = new Bootstrap(userRepository, cardRepository);
        bootstrap.run();

        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository);
    }

    @Test
    public void getBotUserById() throws Exception {
        UserDTO bot = userService.findUserById(Bootstrap.BOT_ID);
        assertEquals(Bootstrap.BOT_NAME, bot.getName());
        assertEquals(Bootstrap.getDefaultDeck().size(), bot.getDeck().size());
    }
}
