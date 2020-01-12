package com.sandura.quiz.controller;

import com.sandura.quiz.model.Question;
import com.sandura.quiz.model.Quiz;
import com.sandura.quiz.repository.CrudQuizRepository;
import com.sandura.quiz.repository.CustomSQLQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "quiz")
public class QuizController {

    private final Logger log = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private CrudQuizRepository crudQuizRepository;

    @Autowired
    private CustomSQLQuestionRepository customSQLQuestionRepository;

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestParam(required = false) String quizName, Model model) {
        Quiz newQuiz = new Quiz();
        newQuiz.setName(quizName);
        crudQuizRepository.save(newQuiz);
        return new ResponseEntity<Quiz>(new Quiz(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Quiz> getQuizByName(@RequestParam(required = false) String quizName) {
        return new ResponseEntity<Quiz>(new Quiz(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "generate")
    public ResponseEntity<Quiz> generateQuiz(@RequestParam String questionCategories, @RequestParam String quizName) {
        log.info("Parameter received {}", questionCategories);
        Quiz generatedQuiz = new Quiz();
        generatedQuiz.setName("Quiz generated via Web");
        List<Question> quizQuestions = new ArrayList<>();
        for (String category : questionCategories.split(",")) {
            quizQuestions.addAll(customSQLQuestionRepository.findByCategory(category));
        }
        generatedQuiz.setQuestionList(quizQuestions);
        return new ResponseEntity<Quiz>(generatedQuiz, HttpStatus.OK);
    }

}
