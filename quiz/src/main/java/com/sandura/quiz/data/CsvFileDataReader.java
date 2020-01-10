package com.sandura.quiz.data;

import com.sandura.quiz.repository.QuestionRepository;
import com.sandura.quiz.model.Answer;
import com.sandura.quiz.model.Question;
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
    private QuestionRepository questionRepository;

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
            tmpQuestion.setDescription(split[2]);
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
            //Format of the data: 1,Unit testing,true
            String[] answerSplit = answerFromFile.split(",");
            String idOfRelatedQuestion = answerSplit[0];
            String answerDescription = answerSplit[1];
            Boolean answerCorrectness = new Boolean(answerSplit[2]);
            Question relatedQuestion;
            Optional<Question> relateQuestionOptional = questionRepository.findById(new Integer(idOfRelatedQuestion));
            if (relateQuestionOptional.isPresent()) {
                relatedQuestion = relateQuestionOptional.get();
            } else {
                throw new Exception("I did not find a Question with id " + idOfRelatedQuestion + " in the question repository.");
            }
            tmpAnswer.setQuestionReference(relatedQuestion);
            tmpAnswer.setCorrectness(answerCorrectness);
            tmpAnswer.setDescription(answerDescription);
            answers.add(tmpAnswer);
        }

        return answers;
    }
}
