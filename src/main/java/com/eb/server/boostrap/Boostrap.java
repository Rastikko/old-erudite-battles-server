package com.eb.server.boostrap;

import com.eb.server.domain.User;
import com.eb.server.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Boostrap implements CommandLineRunner {

    public static final String BOT_NAME = "Bot";

    private final UserRepository userRepository;

    public Boostrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBots();
    }

    private void loadBots() {
        User bot = new User();
        bot.setName(BOT_NAME);
        userRepository.save(bot);

        System.out.println("Bots loaded: " + userRepository.count());
    }
}
