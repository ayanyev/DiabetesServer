package org.coursera.capstone.T1DTeens.helpers;

import org.coursera.capstone.T1DTeens.entities.Relation;
import org.coursera.capstone.T1DTeens.entities.User;
import org.coursera.capstone.T1DTeens.repositories.RelationRepository;
import org.coursera.capstone.T1DTeens.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserHelper {

    @Resource(name="relationRepository")
    RelationRepository relRepo;

    @Resource(name="userRepository")
    UserRepository userRepo;

    public UserHelper() {
    }

    public User addUser(User user){


        return userRepo.save(user);
    }


    public User updateUser(User user){

        return userRepo.save(user);
    }

    public void deleteUser(User user){

        for (Relation relation : relRepo.findBySubscriber(user.getId())){
            relRepo.delete(relation);
        }

        for (Relation relation : relRepo.findBySubscription(user.getId())){
            relRepo.delete(relation);
        }

        userRepo.delete(user);
    }

    public void subscribeUsers(long subscriberId, long subscriptionId){

        Relation rel = relRepo.save(new Relation(subscriberId,
                subscriptionId));
    }

    public void removeSubscription(long subscriberId, long subscriptionId){

        Relation relation = relRepo.findBySubscriberAndSubscription(subscriberId,
                                                                    subscriptionId);

        relRepo.delete(relation);
    }


    public Collection<User> getUserSubscriptionList (long userId){

        Collection<User> subscriptionList = new ArrayList<>();

        for (Relation relation: relRepo.findBySubscriber(userId)){

            subscriptionList.add(userRepo.findOne(relation.getSubscription()));
        }

        return subscriptionList;
    }

    public List<User> getUserSubscribersList (long userId){

        List<User> subscriptionList = new ArrayList<>();

        for (Relation relation: relRepo.findBySubscription(userId)){

            subscriptionList.add(userRepo.findOne(relation.getRel_id()));
        }

        return subscriptionList;
    }

}
