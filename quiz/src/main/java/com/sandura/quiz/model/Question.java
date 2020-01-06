package com.sandura.quiz.model;

import com.sandura.quiz.model.Answer;
import com.sandura.quiz.question.QuestionCategoryEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private QuestionCategoryEnum category;

    private String description;

    @OneToMany
    private List<Answer> answerList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public QuestionCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(QuestionCategoryEnum name) {
        this.category = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String email) {
        this.description = email;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public void addAnswer(Answer newAnswer) {
        if (!this.answerList.contains(newAnswer)) {
            this.answerList.add(newAnswer);
            newAnswer.setQuestionReference(this);
        }
    }

    public String toString() {
        return "[Question with title '" + getCategory() + "' and descripton '" + getDescription() + "']";
    }


}