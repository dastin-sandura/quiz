package com.sandura.quiz;


import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Question questionReference;

    private String description;

    private Boolean isCorrect;

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
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public Boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrectness(Boolean correct) {
        isCorrect = correct;
    }

    public String toString(){
        return "[Answer from category ]";
    }
}
