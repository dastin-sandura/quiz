package com.sandura.quiz.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String category;

    private String description;

    @ManyToMany
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany
    private List<Answer> answerList = new ArrayList<>();

    public Question() {
    }

    public Question(String category, String description) {
        this.category = category;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String name) {
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