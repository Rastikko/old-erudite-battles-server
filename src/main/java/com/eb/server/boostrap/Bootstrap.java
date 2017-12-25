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
        loadCards();
        loadBot();
    }

    private void loadCards() {
        Card pythagorasTheoremCard = new Card();
        Card hamletCard = new Card();

        pythagorasTheoremCard.setId(1L);
        pythagorasTheoremCard.setName("Pythagoras Theorem");

        hamletCard.setId(2L);
        hamletCard.setName("Hamlet");

        cardRepository.save(pythagorasTheoremCard);
        cardRepository.save(hamletCard);

        System.out.println("Cards loaded: " + cardRepository.count());
    }

    private void loadBot() {
        User bot = new User();
        List<Card> deck = getDefaultDeck();

        bot.setName(BOT_NAME);
        bot.setDeck(deck);

        userRepository.save(bot);

        System.out.println("Bots loaded: " + userRepository.count());
    }

    public static List<Card> getDefaultDeck() {
        List<Card> deck = new ArrayList<>();

        Card pythagorasTheoremCard = new Card();
        Card hamletCard = new Card();

        pythagorasTheoremCard.setId(1L);
        hamletCard.setId(2L);

        for(int i = 0; i < 15; i++) {
            deck.add(pythagorasTheoremCard);
            deck.add(hamletCard);
        }

        return deck;
    }
}
