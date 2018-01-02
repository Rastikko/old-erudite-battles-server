package com.eb.server.boostrap;

import com.eb.server.domain.Attribute;
import com.eb.server.domain.types.AttributeType;
import com.eb.server.domain.Card;
import com.eb.server.domain.User;
import com.eb.server.repositories.CardRepository;
import com.eb.server.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    public static final String BOT_NAME = "Bot";
    public static final Long BOT_ID = 1L;

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

        Attribute attackAttribute = new Attribute();
        attackAttribute.setAttributeType(AttributeType.ATTACK);
        attackAttribute.setValue(100);

        List<Attribute> attributes = new ArrayList<>();
        attributes.add(attackAttribute);

        pythagorasTheoremCard.setId(1L);
        pythagorasTheoremCard.setName("Pythagoras Theorem");
        pythagorasTheoremCard.setAttributes(attributes);

        cardRepository.save(pythagorasTheoremCard);

        System.out.println("Cards loaded: " + cardRepository.count());
    }

    private void loadBot() {
        User bot = new User();
        List<Card> deck = getDefaultDeck();

        bot.setId(BOT_ID);
        bot.setName(BOT_NAME);
        bot.setDeck(deck);

        userRepository.save(bot);

        System.out.println("Bots loaded: " + userRepository.count());
    }

    public List<Card> getDefaultDeck() {
        List<Card> deck = new ArrayList<>();
        Card pythagorasTheoremCard = cardRepository.findOne(1L);

        for(int i = 0; i < 30; i++) {
            deck.add(pythagorasTheoremCard);
        }

        return deck;
    }
}
