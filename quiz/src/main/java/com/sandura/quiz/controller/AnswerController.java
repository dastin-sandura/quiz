package com.sandura.quiz.controller;

import com.sandura.quiz.model.Answer;
import com.sandura.quiz.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable Integer id) {
        Optional<Answer> answerOptional = answerRepository.findById(id);
        if (!answerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(answerOptional.get(), HttpStatus.OK);
    }
}
