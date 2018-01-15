package com.eb.server.services;

import com.eb.server.domain.Question;
import com.eb.server.domain.types.QuestionAffinityType;
import com.eb.server.domain.types.QuestionCategoryType;

import java.util.List;

public interface QuestionService {
    Question getRandomQuestion(QuestionCategoryType category, QuestionAffinityType affinity, List<Long> excluded);
}
