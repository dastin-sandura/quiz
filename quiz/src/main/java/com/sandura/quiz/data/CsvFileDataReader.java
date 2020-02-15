package com.sandura.quiz.data;

import com.sandura.quiz.model.Answer;
import com.sandura.quiz.model.Question;
import com.sandura.quiz.repository.CrudQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class CsvFileDataReader {

    private static final Logger log = LoggerFactory.getLogger(CsvFileDataReader.class);

    @Autowired
    private CrudQuestionRepository crudQuestionRepository;

    public ArrayList<Question> readQuestionsFromFile(File questionsCsvFile) throws FileNotFoundException {
        log.info("Reading Question data from file " + questionsCsvFile);
        Question tmpQuestion;
        final ArrayList<Question> questions = new ArrayList<>();
        Scanner questionsScanner = new Scanner(questionsCsvFile);
        questionsScanner.useDelimiter("\n");
        while (questionsScanner.hasNext()) {
            String questionFromFile = questionsScanner.next();
            log.info(questionFromFile);
            tmpQuestion = new Question();
            String[] split = questionFromFile.split(",");
            tmpQuestion.setCategory(split[1]);
            StringBuilder description = new StringBuilder();
            //We iterate over all other tokens in the split to handle cases when Description contains commas
            for (int i = 2; i < split.length; i++) {
                description.append(split[i]);
            }
            tmpQuestion.setDescription(description.toString());
            questions.add(tmpQuestion);
        }
        log.info("Reading Questions data finished. Returning " + questions.size() + " questions.");
        return questions;
    }

    public List<Answer> readAnswersFromFile(File answersCsvFile) throws Exception {
        Answer tmpAnswer;
        ArrayList<Answer> answers = new ArrayList<>();

        Scanner answersScanner = new Scanner(answersCsvFile);
        answersScanner.useDelimiter("\n");
        while (answersScanner.hasNext()) {
            tmpAnswer = new Answer();
            String answerFromFile = answersScanner.next();
            //Format of the data: 1,true,Unit testing
            String[] answerSplit = answerFromFile.split(",");
            String idOfRelatedQuestion = answerSplit[0];
            Boolean answerCorrectness = new Boolean(answerSplit[1]);
            StringBuilder description = new StringBuilder();
            for (int i = 2; i < answerSplit.length; i++) {
                description.append(answerSplit[i]);
            }
            Question relatedQuestion;
            Optional<Question> relateQuestionOptional = crudQuestionRepository.findById(new Integer(idOfRelatedQuestion));
            if (relateQuestionOptional.isPresent()) {
                relatedQuestion = relateQuestionOptional.get();
            } else {
                throw new Exception("I did not find a Question with id " + idOfRelatedQuestion + " in the question repository.");
            }
            tmpAnswer.setQuestionReference(relatedQuestion);
            tmpAnswer.setCorrectness(answerCorrectness);
            tmpAnswer.setDescription(description.toString());
            answers.add(tmpAnswer);
        }

        return answers;
    }
}
