package com.eb.server.boostrap;

import com.eb.server.domain.Card;
import com.eb.server.domain.User;
import com.eb.server.repositories.CardRepository;
import com.eb.server.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Bootstrap implements CommandLineRunner {

    public static final String BOT_NAME = "Bot";

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public Bootstrap(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBot();
    }

    private void loadCards() {
        Card card = new Card();
        card.setName("Pythagoras Theorem");
//        card.setId(1L);

        cardRepository.save(card);
    }

    private void loadBot() {
        Card card = new Card();
        card.setName("Pythagoras Theorem");
        Card savedCard = cardRepository.save(card);

        User bot = new User();
        Set<Card> deck = new HashSet<>();

        deck.add(savedCard);
        bot.setName(BOT_NAME);
        bot.setDeck(deck);

        userRepository.save(bot);

        System.out.println("Bots loaded: " + userRepository.count());
    }

    private void createBotDeck() {

    }
}
