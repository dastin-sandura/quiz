package com.sandura.quiz.service;

import com.sandura.quiz.data.CsvFileDataReader;
import com.sandura.quiz.model.Question;
import com.sandura.quiz.repository.CrudQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {

    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    CrudQuestionRepository crudQuestionRepository;

    @Autowired
    CsvFileDataReader csvFileDataReader;

    public Set<Question> getQuestionsByCategory(int questionsCount, ArrayList<String> selectedCQuestionCategories) {
        Set<Question> onlyTesting = new HashSet<>();
        for (Question question : crudQuestionRepository.findAll()) {
            if (selectedCQuestionCategories.contains(question.getCategory()) && questionsCount > 0) {
                onlyTesting.add(question);
                questionsCount -= 1;
            }
        }
        return onlyTesting;
    }

    public void readAndPersistQuestions(File questionsCsvFile) {
        try {
            List<Question> questions = csvFileDataReader.readQuestionsFromFile(questionsCsvFile);
            crudQuestionRepository.saveAll(questions);
        } catch (FileNotFoundException fileNotFoundException) {
            log.error("FileNotFoundException occurred while reading data from files.");
            log.error(fileNotFoundException.toString());
        } catch (Exception e) {
            log.error("Exception occurred while reading from files.");
            log.error(e.toString());
        }
    }

}
