package com.eb.server.boostrap;

import com.eb.server.domain.User;
import com.eb.server.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;

public class Boostrap implements CommandLineRunner {
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
        bot.setName("Bot");
        userRepository.save(bot);

        System.out.println("Bots loaded: " + userRepository.count());
    }
}
