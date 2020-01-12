package com.sandura.quiz.data;

import com.sandura.quiz.service.AnswerService;
import com.sandura.quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CsvStarterDatasetImporter implements StarterDatasetImporter {

    @Value("${question.data.csv.relative-file-path}")
    private String QUESTIONS_CSV_FILE_DIRECTORY;

    @Value("${answer.data.csv.relative-file-path}")
    private String ANSWERS_CSV_FILE_DIRECTORY;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    public void populateDatabaseWithStarterDataset() {
        File questionsCsvFile = new File(QUESTIONS_CSV_FILE_DIRECTORY);
        File answersCsvFile = new File(ANSWERS_CSV_FILE_DIRECTORY);

        questionService.readAndPersistQuestions(questionsCsvFile);
        answerService.readAndPersistAnswers(answersCsvFile);
    }

}
