package com.sandura.quiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

@Controller
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewQuestion(@RequestParam String title, @RequestParam String description) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Question n = new Question();
        n.setCategory(title);
        n.setDescription(description);
        questionRepository.save(n);

        log.info("Saving Question " + n + " in the database.");

        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Question> getAllQuestions() {
        // This returns a JSON or XML with the users
        log.info("Returning all questions");
        return questionRepository.findAll();
    }

    @GetMapping(path = "populate")
    public String populateDatabaseWithQuestionsFromFile(Model model) {
        final ArrayList<Question> questions = new ArrayList<>();
        String questionsCsvFileDirectory = "./src/main/resources/questions.csv";
        File questionsCsvFile = new File(questionsCsvFileDirectory);
        Question tmpQuestion;

        String answersCsvFileDirectory = "./src/main/resources/answers.csv";
        File answersCsvFile = new File(answersCsvFileDirectory);
        Answer tmpAnswer;
        try {
            //Persist Questions
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
            questionRepository.saveAll(questions);

            tmpAnswer = new Answer();
            Scanner answersScanner = new Scanner(answersCsvFile);
            answersScanner.useDelimiter("\n");
            while (answersScanner.hasNext()) {
                String answerFromFile = answersScanner.next();
                //1, Unit testing, true
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
                answerRepository.save(tmpAnswer);
                questionRepository.save(relatedQuestion);

            }

        } catch (FileNotFoundException fileNotFoundException) {
            log.error("FileNotFoundException occured while reading file {}", questionsCsvFileDirectory);
            log.error(fileNotFoundException.toString());
        } catch (Exception e) {
            log.error("Exception occured while reading file {}", questionsCsvFileDirectory);
            log.error(e.toString());
        }
        model.addAttribute("pathname", questionsCsvFile.getAbsoluteFile());
        return "populate";
    }

}
