package com.sandura.quiz;

import javax.persistence.*;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String category;

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    private String description;

    @OneToMany
    private List<Answer> answerList;

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

    public String toString() {
        return "[Question with title '" + getCategory() + "' and descripton '" + getDescription() + "']";
    }


}