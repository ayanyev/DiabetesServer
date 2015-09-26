package org.coursera.capstone.T1DTeens.repositories;

import org.coursera.capstone.T1DTeens.entities.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionsRepository extends CrudRepository<Question, Long> {
}
