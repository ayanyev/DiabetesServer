package org.coursera.capstone.T1DTeens.entities;

import org.coursera.capstone.T1DTeens.AnswerType;

import javax.persistence.*;

@Entity
@Table(name="QUESTIONS")
public class Question {

    @Id
    @Column(name="QUESTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="TEXT")
    private String text;

    @Column(name="ANSWER_TYPE")
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    @Column(name="REQUIRED")
    private Boolean required;

//    @OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL)
//    private List<Answer> answers = new ArrayList<>();
//
//    @OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL)
//    private List<Option> options = new ArrayList<>();

    public Question() {}

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
//
//    public List<Option> getOptions() {
//        return options;
//    }
//
//    public void setOptions(List<Option> options) {
//        this.options = options;
//    }
//
//    public List<Answer> getAnswers() {
//        return answers;
//    }
//
//    public void setAnswers(List<Answer> answers) {
//        this.answers = answers;
//    }

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
}
