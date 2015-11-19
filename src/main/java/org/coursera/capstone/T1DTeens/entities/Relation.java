package org.coursera.capstone.T1DTeens.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.coursera.capstone.T1DTeens.entities.enums.RelationStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name="relations", schema = "", catalog = "t1dteens")
public class Relation {

    @Id
    @Column(name="relation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private long rel_id;

    @Column(name = "subscriber", nullable = false)
    private long subscriber;

    @Column(name = "subscription", nullable = false)
    private long subscription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RelationStatus status;

    @Column(name="timestamp")
    private Timestamp timestamp;

    public Relation() {
    }

    public Relation(long subscriber, long subscription) {
        this.subscriber = subscriber;
        this.subscription = subscription;
        this.status = RelationStatus.PENDING;
    }

    public long getSubscription() {
        return subscription;
    }

    public void setSubscription(long subscription) {
        this.subscription = subscription;
    }

    public long getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(long subscriber) {
        this.subscriber = subscriber;
    }

    public long getRel_id() {
        return rel_id;
    }

    public void setRel_id(long rel_id) {
        this.rel_id = rel_id;
    }

    public RelationStatus getStatus() {
        return status;
    }

    public void setStatus(RelationStatus status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return Objects.equals(subscriber, relation.subscriber) &&
                Objects.equals(subscription, relation.subscription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriber, subscription);
    }
}
