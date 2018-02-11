package com.eb.server.bootstrap;

import com.eb.server.domain.Attribute;
import com.eb.server.domain.Card;
import com.eb.server.domain.Question;
import com.eb.server.domain.User;
import com.eb.server.repositories.AttributeRepository;
import com.eb.server.repositories.CardRepository;
import com.eb.server.repositories.QuestionRepository;
import com.eb.server.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    public static final String BOT_NAME = "Bot";
    public static final Long BOT_ID = 1L;

    private final UserRepository userRepository;
    private final AttributeRepository attributeRepository;
    private final CardRepository cardRepository;
    private final QuestionRepository questionRepository;

    private final ResourceLoader resourceLoader;

    public Bootstrap(ResourceLoader resourceLoader,
                     UserRepository userRepository,
                     AttributeRepository attributeRepository,
                     CardRepository cardRepository,
                     QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.attributeRepository = attributeRepository;
        this.cardRepository = cardRepository;
        this.questionRepository = questionRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) {
        if (cardRepository.count() == 0) {
            loadAttributes();
            loadCards();
            loadBot();
            loadQuestions();
        }
    }

    private void loadAttributes() {
        try {
            Resource resource = resourceLoader.getResource("classpath:fixtures/attributes.json");
            File file = resource.getFile();
            ObjectMapper mapper = new ObjectMapper();
            Attribute[] attributes = mapper.readValue(file, Attribute[].class);
            for (Attribute attribute : attributes) {
                attributeRepository.save(attribute);
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Error loading fixtures/attributes: " + e);
        }

        System.out.println("Attributes loaded: " + attributeRepository.count());

    }

    private void loadCards() {
        try {
            Resource resource = resourceLoader.getResource("classpath:fixtures/cards.json");
            File cardsFile = resource.getFile();
            ObjectMapper mapper = new ObjectMapper();
            Card[] cards = mapper.readValue(cardsFile, Card[].class);
            for (Card card : cards) {
                for (int i = 0; i < card.getAttributes().size(); i++) {
                    Attribute attribute = card.getAttributes().get(i);
                    Attribute refAttribute = attributeRepository.findOne(attribute.getId());
                    card.getAttributes().set(i, refAttribute);
                }
                cardRepository.save(card);
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Error loading fixtures/cards: " + e);
        }

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

    private void loadQuestions() {
        try {
            Resource resource = resourceLoader.getResource("classpath:fixtures/questions.json");
            File file = resource.getFile();
            ObjectMapper mapper = new ObjectMapper();
            Question[] attributes = mapper.readValue(file, Question[].class);
            for (Question question : attributes) {
                questionRepository.save(question);
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Error loading fixtures/attributes: " + e);
        }

        System.out.println("Questions loaded: " + questionRepository.count());

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
