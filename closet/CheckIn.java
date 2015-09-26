package org.coursera.capstone.T1DTeens.entities;

import org.coursera.capstone.T1DTeens.CheckInStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CHECKINS")
public class CheckIn {

    @Id
    @Column(name="CHECKIN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "USER_ID")
    private User teen;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private CheckInStatus status;

//    @OneToMany(mappedBy = "checkInId", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public CheckIn() {
    }

    public CheckIn(User teen) {
        this.teen = teen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getTeen() {
        return teen;
    }

    public void setTeen(User teen) {
        this.teen = teen;
    }

    public CheckInStatus getStatus() {
        return status;
    }

    public void setStatus(CheckInStatus status) {
        this.status = status;
    }
}
