package com.eb.server.services;

import com.eb.server.domain.Question;
import com.eb.server.domain.types.QuestionAffinityType;
import com.eb.server.domain.types.QuestionCategoryType;
import com.eb.server.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuestionServiceImpl implements QuestionService {
    QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question getRandomQuestion(QuestionCategoryType category, QuestionAffinityType affinity, List<Long> excluded) {
        List<Question> questions = questionRepository.findByCategoryAndAffinityAndIdNotIn(category, affinity, excluded);
        Random r = new Random();
        return questions.get(r.nextInt(questions.size()));
    }
}
