package com.sandura.quiz.repository;

import com.sandura.quiz.model.Question;
import org.springframework.data.repository.CrudRepository;

public interface CrudQuestionRepository extends CrudRepository<Question, Integer> {

}