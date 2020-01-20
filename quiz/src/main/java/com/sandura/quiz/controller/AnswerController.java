package com.sandura.quiz.controller;

import com.sandura.quiz.model.Answer;
import com.sandura.quiz.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "answer")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> allAnswers = new ArrayList<>();
        for (Answer answer : answerRepository.findAll()) {
            allAnswers.add(answer);
        }
        return new ResponseEntity<>(allAnswers, HttpStatus.OK);
    }
}
