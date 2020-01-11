package com.sandura.quiz.controller;

import com.sandura.quiz.model.Quiz;
import com.sandura.quiz.repository.CrudQuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "quiz")
public class QuizController {

    private final Logger log = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private CrudQuizRepository crudQuizRepository;

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestParam String quizName) {
        Quiz newQuiz = new Quiz();
        newQuiz.setName(quizName);
        crudQuizRepository.save(newQuiz);
        return new ResponseEntity<Quiz>(new Quiz(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Quiz> getQuizByName(@RequestParam String quizName) {
        return new ResponseEntity<Quiz> (new Quiz(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "generate")
    public ResponseEntity<Quiz> generateQuiz(@RequestParam String questionCategories) {
        log.info("Parameter received {}", questionCategories);
        return new ResponseEntity<Quiz>(new Quiz(), HttpStatus.OK);
    }

}
