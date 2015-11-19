package org.coursera.capstone.T1DTeens.repositories;

import org.coursera.capstone.T1DTeens.entities.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Transactional
@Repository
public interface OptionsRepository extends CrudRepository<Option, Long> {

    List<Option> findByTimestampGreaterThan(Timestamp lastUpdateTime);
}
