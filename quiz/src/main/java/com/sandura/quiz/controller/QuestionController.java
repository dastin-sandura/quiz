package com.sandura.quiz.controller;

import com.sandura.quiz.repository.CustomSQLQuestionRepository;
import com.sandura.quiz.repository.CrudQuestionRepository;
import com.sandura.quiz.model.Question;
import com.sandura.quiz.service.AnswerService;
import com.sandura.quiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

@Controller
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private CrudQuestionRepository crudQuestionRepository;

    @Autowired
    AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Value("${question.data.csv.relative-file-path}")
    private String QUESTIONS_CSV_FILE_DIRECTORY;

    @Value("${answer.data.csv.relative-file-path}")
    private  String ANSWERS_CSV_FILE_DIRECTORY;

    @Autowired
    private CustomSQLQuestionRepository customSQLQuestionRepository;

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewQuestion(@RequestParam String title, @RequestParam String description) {
        Question n = new Question();
        n.setCategory(title);
        n.setDescription(description);
        crudQuestionRepository.save(n);
        log.info("Saving Question " + n + " in the database.");
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Question> getAllQuestions() {
        log.info("Returning all questions");
        return crudQuestionRepository.findAll();
    }

    @GetMapping(path = "/allsql")
    public @ResponseBody Iterable<Question> getAllQuestionWithSQL(){
        log.info("Returning all questions via SQL from JdbcTemplate");
        return customSQLQuestionRepository.findAll();

    }

    @GetMapping(path = "/alltesting")
    public @ResponseBody
    Set<Question> getQuestionsByCategory(@RequestParam int questionsCount, @RequestParam String category) {
        setDefaultValuesIfParametersNotProvided(questionsCount, category);
        ArrayList<String> selectedCategories = new ArrayList<>();
        selectedCategories.add(category);
        return questionService.getQuestionsByCategory(questionsCount, selectedCategories);
    }

    @GetMapping(path = "populate")
    public String populateDatabaseWithQuestionsFromFile() {
        File questionsCsvFile = new File(QUESTIONS_CSV_FILE_DIRECTORY);
        File answersCsvFile = new File(ANSWERS_CSV_FILE_DIRECTORY);

        //TODO add a software pattern which will remove the need to pass the file paths.
        //TODO Probably strategy or command pattern
        questionService.readAndPersistQuestions(questionsCsvFile);
        answerService.readAndPersistAnswers(answersCsvFile);
        return "populate";
    }

    private void setDefaultValuesIfParametersNotProvided(int questionsCount, String category) {
        if (questionsCount == 0) {
            questionsCount = 2;
        }
        if (category == null) {
            category = "Testing";
        }
    }

}
