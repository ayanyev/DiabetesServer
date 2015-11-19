package org.coursera.capstone.T1DTeens.entities;

import org.coursera.capstone.T1DTeens.entities.enums.AnswerType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="questions", schema = "", catalog = "t1dteens")
public class Question {

    @Id
    @Column(name="question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="text")
    private String text;

    @Column(name="answer_type")
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    @Column(name="required")
    private Boolean required;

    @Column(name="rateable")
    private Boolean rateable;

    @Column(name="showorder")
    private int order;

    @Column(name="timestamp")
    private Timestamp timestamp;

    @OneToMany(cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
    @JoinColumn(name="question_id")
    private List<Option> options = new ArrayList<>();

    public Question() {}

    public Boolean getRateable() {
        return rateable;
    }

    public void setRateable(Boolean rateable) {
        this.rateable = rateable;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
