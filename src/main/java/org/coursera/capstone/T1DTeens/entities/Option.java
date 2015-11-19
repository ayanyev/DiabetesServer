package org.coursera.capstone.T1DTeens.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="options", schema = "", catalog = "t1dteens")
public class Option {

    @Id
    @Column(name="option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="question_id")
    private int questionId;

    @Column(name="text")
    private String text;

    @Column(name="weight")
    private byte weight;

    @Column(name="timestamp")
    private Timestamp timestamp;

    public Option() {
    }

    public Option(String text, int questionId) {
        this.text = text;
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public byte getWeight() {
        return weight;
    }

    public void setWeight(byte weight) {
        this.weight = weight;
    }
}
