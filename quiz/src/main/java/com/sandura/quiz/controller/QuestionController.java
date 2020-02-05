package com.sandura.quiz.controller;

import com.sandura.quiz.model.Question;
import com.sandura.quiz.repository.CrudQuestionRepository;
import com.sandura.quiz.repository.CustomSQLQuestionRepository;
import com.sandura.quiz.service.AnswerService;
import com.sandura.quiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path = "question")
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private CrudQuestionRepository crudQuestionRepository;

    @Autowired
    AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CustomSQLQuestionRepository customSQLQuestionRepository;

    @PostMapping(path = "add")
    public @ResponseBody
    String addNewQuestion(@RequestParam String title, @RequestParam String description) {
        Question n = new Question();
        n.setCategory(title);
        n.setDescription(description);
        crudQuestionRepository.save(n);
        log.info("Saving Question " + n + " in the database.");
        return "Saved";
    }

    @GetMapping(path = "all")
    public @ResponseBody
    ResponseEntity<Iterable<Question>> getAllQuestions() {
        log.info("Returning all questions");
        return new ResponseEntity<>(crudQuestionRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "allsql")
    public @ResponseBody
    ResponseEntity<Iterable<Question>> getAllQuestionWithSQL() {
        log.info("Returning all questions via SQL from JdbcTemplate");
        return new ResponseEntity<>(customSQLQuestionRepository.findAll(), HttpStatus.OK);

    }

    @GetMapping(path = "alltesting")
    public @ResponseBody
    ResponseEntity<Set<Question>> getQuestionsByCategory(@RequestParam int questionsCount, @RequestParam String category) {
        ArrayList<String> selectedCategories = new ArrayList<>();
        selectedCategories.add(category);
        customSQLQuestionRepository.getQuestionCount();
        return new ResponseEntity<>(questionService.getQuestionsByCategory(questionsCount, selectedCategories), HttpStatus.OK);
    }

    @GetMapping(path = "populate")
    public String populateDatabaseWithQuestionsFromFile() {
        return "index";
    }

    @GetMapping(path = "count")
    public ResponseEntity<Integer> getQuestionCount() {
        return new ResponseEntity<>(customSQLQuestionRepository.getQuestionCount(), HttpStatus.OK);
    }

    @GetMapping(path = "random/{count}")
    public ResponseEntity<List<Question>> getRandomQuestion(@PathVariable int count) {
        return new ResponseEntity<List<Question>>(customSQLQuestionRepository.getRandomQuestionsFromCategory(count, "Java"), HttpStatus.OK);
    }

}
