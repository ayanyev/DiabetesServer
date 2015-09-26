package org.coursera.capstone.T1DTeens.entities;

@Entity
@Table(name="OPTIONS")
public class Option {

    @Id
    @Column(name="_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="TEXT")
    private String text;

//    @ManyToOne
//    @JoinColumn(name = "QUESTION_ID")
    private Question questionId;

    public Option() {
    }

    public Option(String text, Question questionId) {
        this.text = text;
        this.questionId = questionId;
    }
}
