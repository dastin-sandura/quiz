package com.sandura.quiz.controller;

import com.sandura.quiz.model.Question;
import com.sandura.quiz.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    @PostMapping(path = "quiz")
    public ResponseEntity<Quiz> createQuiz() {
        return new ResponseEntity<Quiz>(new Quiz(), HttpStatus.OK);
    }
    @GetMapping(path = "quiz")
    public ResponseEntity<Quiz> getQuizByName(@RequestParam String quizName) {
        return new ResponseEntity<Quiz> (new Quiz(), HttpStatus.BAD_REQUEST);
    }
}
