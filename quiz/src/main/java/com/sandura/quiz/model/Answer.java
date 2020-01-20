package com.sandura.quiz.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //Ignore to prevent infinite reference loops
    @JsonIgnore
    @ManyToOne
    private Question questionReference;

    private String description;

    private Boolean isCorrect;

    public Answer (){

    }

    public Answer (String description, Boolean isCorrect) {
        this.description = description;
        this.isCorrect = isCorrect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question getQuestionReference() {
        return questionReference;
    }

    public void setQuestionReference(Question questionReference) {
        this.questionReference = questionReference;
        questionReference.addAnswer(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void setCorrectness(Boolean correct) {
        isCorrect = correct;
    }

    public String toString() {
        return "[Answer from category ]";
    }
}
