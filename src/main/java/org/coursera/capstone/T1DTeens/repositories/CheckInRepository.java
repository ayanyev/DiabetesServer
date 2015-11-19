package org.coursera.capstone.T1DTeens.repositories;

import org.coursera.capstone.T1DTeens.entities.CheckIn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
//@Repository
/*@RepositoryRestResource(itemResourceRel = "relation",
                        collectionResourceRel = "checkIns",
                        path = "checkins")*/
public interface CheckInRepository extends CrudRepository<CheckIn, Long> {

//    @Query("select u from CheckIn u where u.timestamp > :lastUpdateTime and u.user_id = :userId")
    List<CheckIn> findByUserId(Long userId);
}
