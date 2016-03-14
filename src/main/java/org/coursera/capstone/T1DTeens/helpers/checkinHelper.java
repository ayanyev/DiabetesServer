package org.coursera.capstone.T1DTeens.helpers;

import org.coursera.capstone.T1DTeens.entities.CheckIn;
import org.coursera.capstone.T1DTeens.repositories.AnswersRepository;
import org.coursera.capstone.T1DTeens.repositories.CheckInRepository;
import org.coursera.capstone.T1DTeens.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CheckInHelper {

    @Autowired
    CheckInRepository checkInRepo;

    @Autowired
    QuestionsRepository qRepo;

    @Autowired
    AnswersRepository aRepo;

    public CheckInHelper() {
    }

    public CheckIn addCheckIn(CheckIn ci){

        ci = checkInRepo.save(ci);

        return checkInRepo.findOne(ci.getId());
    }

    public CheckIn updateCheckIn(CheckIn ci){

        ci = checkInRepo.save(ci);

        return checkInRepo.findOne(ci.getId());
    }

    public void deleteCheckIn(CheckIn ci){

        checkInRepo.delete(ci);
    }

/*    public List<CheckIn> getUserCheckIns(User user) {

        return checkInRepo.findByUser(user);
    }*/
}
