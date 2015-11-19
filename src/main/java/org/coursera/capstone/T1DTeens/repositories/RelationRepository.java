package org.coursera.capstone.T1DTeens.repositories;

import org.coursera.capstone.T1DTeens.entities.Relation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Transactional
@Repository
public interface RelationRepository extends CrudRepository<Relation, Long> {


    Relation findBySubscriberAndSubscription(Long userId, Long subscriptionId);

    List<Relation> findBySubscriber(Long userId);

    List<Relation> findBySubscription(Long userId);

    @Query("select u from Relation u where u.timestamp > :lastUpdateTime and (u.subscriber = :userId or u.subscription = :userId)")
    List<Relation> findByTimestampGreaterThanAndUserId(@Param("lastUpdateTime") Timestamp lastUpdateTime,
                                                       @Param("userId") Long userId);

}
