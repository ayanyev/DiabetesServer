package org.coursera.capstone.T1DTeens.entities;

import javax.persistence.*;

@Entity
@Table(name="ANSWERS")
public class Answer {

    @Id
    @Column(name="ANSWER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "QUESTION_ID")
//    private Question questionId;

//    @ManyToOne
//    @JoinColumn(name = "CHECKIN_ID")
//    private CheckIn checkInId;

    @Column(name="TEXT")
    private String text;

    public Answer() {
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
}
