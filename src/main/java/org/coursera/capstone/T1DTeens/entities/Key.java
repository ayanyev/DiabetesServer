package org.coursera.capstone.T1DTeens.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Key implements Serializable{

    @Column(name="USER", nullable = false)
    private long userId;

    @Column(name="SUBSCRIBEDTO", nullable = false)
    private long subscribedToId;

    public Key() {
    }

    public Key(long userId, long subscribedToId) {
        this.userId = userId;
        this.subscribedToId = subscribedToId;
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
