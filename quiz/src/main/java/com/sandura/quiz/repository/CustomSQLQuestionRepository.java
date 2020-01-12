package com.sandura.quiz.repository;

import com.sandura.quiz.model.Answer;
import com.sandura.quiz.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomSQLQuestionRepository{

    private static final Logger log = LoggerFactory.getLogger(CustomSQLQuestionRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Iterable<Question> findAll() {
        List<Question> questions = new ArrayList();
        try {
            /*
              SELECT which returns each each combination of question and it's answers
              e.g of question has 2 answers there will be 2 rows with this question and each will have different answer
              if a question has 4 answers, this question will appear 4 teams and each row will have unique answer
              Example result
                ID  CATEGORY ▲	QUESTION_DESCRIPTION  	                                ANSWER_DESCRIPTION  	IS_CORRECT
                1	Testing	    1Which of these are correct types of software tests?	1Unit testing	        TRUE
                1	Testing	    1Which of these are correct types of software tests?	1Integration testing	TRUE
                2	Java	    2What are default data types available in Java?	        2boolean	            TRUE
                2	Java	    2What are default data types available in Java?	        2Boolean	            TRUE
              */
            String sqlWithJoin = "SELECT question.id AS QUESTION_ID, question.category," +
                    " question.description as question_description," +
                    " answer.id AS ANSWER_ID, answer.description as answer_description, answer.is_correct " +
                    "FROM QUESTION INNER JOIN answer ON question.id = answer.question_reference_id";
            Map<Integer, Question> existingQuestions = new HashMap<>();
            jdbcTemplate.query(sqlWithJoin,
                    (rs, rownum) -> {
                        Question tmpQuestion;
                        Integer databaseQuestionId = rs.getInt("QUESTION_ID");
                        if (existingQuestions.containsKey(databaseQuestionId)) {
                            tmpQuestion = existingQuestions.get(databaseQuestionId);
                        } else {
                            tmpQuestion = new Question();
                            existingQuestions.put(databaseQuestionId, tmpQuestion);
                            tmpQuestion.setId(databaseQuestionId);
                            tmpQuestion.setCategory(rs.getString("CATEGORY"));
                            tmpQuestion.setDescription(rs.getString("QUESTION_DESCRIPTION"));
                        }
                        Answer tmpAnswer = new Answer();
                        tmpAnswer.setId(rs.getInt("ANSWER_ID"));
                        tmpAnswer.setDescription(rs.getString("ANSWER_DESCRIPTION"));
                        tmpAnswer.setIsCorrect(rs.getBoolean("IS_CORRECT"));
                        tmpQuestion.addAnswer(tmpAnswer);
                        return tmpQuestion;
                    }
            );
            questions.addAll(existingQuestions.values());
        } catch (Exception e) {
            log.error("Exception while reading all questions data from database.");
        }
        return questions;
    }

}
