package com.sandura.quiz.repository;

import com.sandura.quiz.model.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
}
