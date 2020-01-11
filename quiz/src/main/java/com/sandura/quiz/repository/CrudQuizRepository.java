package com.sandura.quiz.repository;

import com.sandura.quiz.model.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface CrudQuizRepository extends CrudRepository<Quiz, Integer> {
}
