package com.eb.server.boostrap;

import com.eb.server.domain.Attribute;
import com.eb.server.domain.Question;
import com.eb.server.domain.types.AttributeType;
import com.eb.server.domain.Card;
import com.eb.server.domain.User;
import com.eb.server.domain.types.QuestionAffinityType;
import com.eb.server.domain.types.QuestionCategoryType;
import com.eb.server.repositories.CardRepository;
import com.eb.server.repositories.QuestionRepository;
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
    private final QuestionRepository questionRepository;

    public Bootstrap(UserRepository userRepository, CardRepository cardRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCards();
        loadBot();
        loadQuestions();
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
        pythagorasTheoremCard.setCost(1);

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

    private void loadQuestions() {
        Question question = new Question();
        question.setCategory(QuestionCategoryType.TRIGONOMETRY);
        question.setAffinity(QuestionAffinityType.LOGIC);
        question.setTitle("What is sin(90 grade)");
        question.setCorrectAnswer("1");
        question.setAverageAnswerTime(20);

        questionRepository.save(question);

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
