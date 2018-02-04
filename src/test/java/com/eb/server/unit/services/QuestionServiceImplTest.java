package com.eb.server.unit.services;

import com.eb.server.domain.Question;
import com.eb.server.domain.types.QuestionSubcategoryType;
import com.eb.server.domain.types.QuestionCategoryType;
import com.eb.server.repositories.QuestionRepository;
import com.eb.server.services.QuestionService;
import com.eb.server.services.QuestionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class QuestionServiceImplTest {

    QuestionService questionService;

    @Mock
    QuestionRepository questionRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        List<Question> questions = getQuestions();
        when(questionRepository.findByCategory(Matchers.any(QuestionCategoryType.class))).thenReturn(questions);

        questionService = new QuestionServiceImpl(questionRepository);
    }

    @Test
    public void getRandomQuestion() {
        Question question = questionService.getRandomQuestion(QuestionCategoryType.LOGIC, QuestionSubcategoryType.TRIGONOMETRY, new ArrayList<>());
        assertNotNull(question.getId());
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        Question firstQuestion = new Question();
        Question secondQuestion = new Question();
        firstQuestion.setId(1L);
        secondQuestion.setId(2L);
        questions.add(firstQuestion);
        questions.add(secondQuestion);
        return questions;
    }
}
