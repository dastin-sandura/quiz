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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "quiz")
public class QuizController {

    private static final Logger log = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private CrudQuizRepository crudQuizRepository;

    @Autowired
    private CustomSQLQuestionRepository customSQLQuestionRepository;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> allQuizzes = new ArrayList<>();
        for (Quiz quiz : crudQuizRepository.findAll()) {
            allQuizzes.add(quiz);
        }
        return ResponseEntity.ok(allQuizzes);
    }

    @GetMapping(path = "{quiz_id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Integer quiz_id) {
        Optional<Quiz> byId = crudQuizRepository.findById(quiz_id);
        if (byId.isPresent()) {
            return ResponseEntity.of(byId);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestParam String questionCategories, @RequestParam String quizName, @RequestParam int questionCount) {
        log.info("Quiz categories parameter received {}", questionCategories);
        Quiz generatedQuiz = new Quiz();
        generatedQuiz.setName(quizName);
        List<Question> quizQuestions = new ArrayList<>();
        for (String category : questionCategories.split(",")) {
            quizQuestions.addAll(customSQLQuestionRepository.findQuestionsByCategory(category));
        }
        generatedQuiz.setQuestionList(quizQuestions);
        crudQuizRepository.save(generatedQuiz);
        return new ResponseEntity<Quiz>(generatedQuiz, HttpStatus.OK);
    }

}
