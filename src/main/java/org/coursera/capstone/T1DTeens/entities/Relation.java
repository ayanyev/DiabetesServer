package org.coursera.capstone.T1DTeens.entities;

import javax.persistence.*;

@Entity
@Table(name="RELATIONS")
public class Relation {

    @Id
    @Column(name="REL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="USER")
    private long userId;

    @Column(name="SUBSCRIBEDTO")
    private long subscribedToId;

    public Relation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSubscribedToId() {
        return subscribedToId;
    }

    public void setSubscribedToId(long subscribedToId) {
        this.subscribedToId = subscribedToId;
    }
}
