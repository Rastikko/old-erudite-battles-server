package com.eb.server.repositories;

import com.eb.server.domain.Question;
import com.eb.server.domain.types.QuestionCategoryType;
import com.eb.server.domain.types.QuestionSubcategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryAndSubCategoryAndIdNotIn(QuestionCategoryType categoryType, QuestionSubcategoryType subcategoryType, List<Long> ids);
}
