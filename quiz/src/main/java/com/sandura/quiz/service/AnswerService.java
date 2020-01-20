package com.sandura.quiz.service;

import com.sandura.quiz.data.CsvFileDataReader;
import com.sandura.quiz.model.Answer;
import com.sandura.quiz.repository.AnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class AnswerService {

    private static final Logger log = LoggerFactory.getLogger(AnswerService.class);

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    CsvFileDataReader csvFileDataReader;

    public void readAndPersistAnswers(File answersCsvFile) {
        try {
            List<Answer> answers = csvFileDataReader.readAnswersFromFile(answersCsvFile);
            answerRepository.saveAll(answers);
        } catch (FileNotFoundException fileNotFoundException) {
            log.error("FileNotFoundException occurred while reading data from files.");
            log.error(fileNotFoundException.toString());
        } catch (Exception e) {
            log.error("Exception occurred while reading from files.");
            log.error(e.toString());
        }
    }

}
