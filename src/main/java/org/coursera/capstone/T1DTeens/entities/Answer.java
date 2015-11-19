package org.coursera.capstone.T1DTeens.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="answers", schema = "", catalog = "t1dteens")
public class Answer {

    @Id
    @Column(name="answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answer_id;

    @Column(name="question_id")
    private int questionId;

    @Column(name="checkin_id")
    private Long checkInId;

    @Column(name="text")
    private String text;

    @Column(name="value")
    private Integer value;

    @Column(name="timestamp")
    private Timestamp timestamp;

    public Answer() {}

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(long answer_id) {
        this.answer_id = answer_id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Long getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(Long checkInId) {
        this.checkInId = checkInId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
