package com.eb.server.services;

import com.eb.server.domain.Question;
import com.eb.server.domain.types.QuestionCategoryType;
import com.eb.server.domain.types.QuestionSubcategoryType;

import java.util.List;

public interface QuestionService {
    Question getRandomQuestion(QuestionCategoryType category, QuestionSubcategoryType subcategory, List<Long> excluded);
}
