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
import org.springframework.ui.Model;
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

    /**
     * Endpoint without @ResponseBody to make it return a name of Thymeleaf template
     *
     * @param model
     * @return
     */
    @GetMapping
    public String getAllQuestionTemplate(Model model) {
        Iterable<Question> allQuestions = crudQuestionRepository.findAll();
        model.addAttribute("questions", allQuestions);
        return "questions-list";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Question> addNewQuestion(@RequestParam String title, @RequestParam String description) {
        Question newQuestion = new Question();
        newQuestion.setCategory(title);
        newQuestion.setDescription(description);
        crudQuestionRepository.save(newQuestion);
        log.info("Saving Question " + newQuestion + " in the database.");
        return new ResponseEntity<>(newQuestion, HttpStatus.OK);
    }

    @GetMapping(path = "allsql")
    @ResponseBody
    public ResponseEntity<Iterable<Question>> getAllQuestionWithSQL() {
        log.info("Returning all questions via SQL from JdbcTemplate");
        return new ResponseEntity<>(customSQLQuestionRepository.findAll(), HttpStatus.OK);

    }

    @GetMapping(path = "/category/{category}/{questionsCount}")
    @ResponseBody
    public ResponseEntity<Set<Question>> getQuestionsByCategory(@PathVariable int questionsCount, @PathVariable String category) {
        ArrayList<String> selectedCategories = new ArrayList<>();
        selectedCategories.add(category);
        customSQLQuestionRepository.getQuestionCount();
        return new ResponseEntity<>(questionService.getQuestionsByCategory(questionsCount, selectedCategories), HttpStatus.OK);
    }

    @GetMapping(path = "/statistics/count")
    @ResponseBody
    public ResponseEntity<Integer> getQuestionCount() {
        return new ResponseEntity<>(customSQLQuestionRepository.getQuestionCount(), HttpStatus.OK);
    }

    @GetMapping(path = "random/{count}")
    @ResponseBody
    public ResponseEntity<List<Question>> getRandomQuestion(@PathVariable int count) {
        return new ResponseEntity<>(customSQLQuestionRepository.getRandomQuestionsFromCategory(count, "Java"), HttpStatus.OK);
    }

}
