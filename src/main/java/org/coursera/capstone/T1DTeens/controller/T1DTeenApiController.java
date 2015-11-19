package org.coursera.capstone.T1DTeens.controller;

import com.google.common.collect.Lists;
import org.coursera.capstone.T1DTeens.entities.*;
import org.coursera.capstone.T1DTeens.entities.enums.UserType;
import org.coursera.capstone.T1DTeens.helpers.CheckInHelper;
import org.coursera.capstone.T1DTeens.helpers.UserHelper;
import org.coursera.capstone.T1DTeens.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.coursera.capstone.T1DTeens.controller.RequestResult.Message;

@Controller
public class T1DTeenApiController {

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private QuestionsRepository qRepo;

    @Autowired
    private RelationRepository rRepo;

    @Autowired
    private OptionsRepository oRepo;

    @Autowired
    private CheckInRepository ciRepo;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private CheckInHelper checkinHelper;

    // get user list

    @RequestMapping(value="/users/type/{type}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody RequestResult
    getUserList(@PathVariable("type") UserType userType, Principal p) {

        List<User> usersList;
        try {
            if (userType == UserType.ALL){
                usersList = Lists.newArrayList(uRepo.findAll());
            } else {
                usersList = Lists.newArrayList(uRepo.findByUserType(userType));
            }
            for (User user : usersList) {
                user.enforceSharePolicy();
            }
            return new RequestResult(Message.OK, usersList);
        } catch (Exception e) {
            return new RequestResult(Message.SERVER_ERROR);
        }
    }

    // get one user by id

    @RequestMapping(value="/users/{id}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody ResponseEntity<User>
    getUserById(@PathVariable("id") long id) {

        User user = uRepo.findOne(id);

        if (user != null) {

            user.enforceSharePolicy();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/users/bycredentials", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody RequestResult
    getUserByCredentials(@RequestBody User user) {

        User user1 = uRepo.findByUsername(user.getUsername());

        if (user1 == null)
            return new RequestResult(Message.USER_NOT_FOUND);
        else if (user.equals(user1))
            return new RequestResult(Message.USER_ACTIVE, user1);
        else if (!user.getPassword().equals(user1.getPassword()))
            return new RequestResult(Message.WRONG_PASSWORD);
        else
            return new RequestResult(Message.USER_NOT_FOUND);
    }

    // add user

//    @PreAuthorize("hasAnyRole('role_admin','role_guest')")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public
    @ResponseBody RequestResult
    addUser(@RequestBody User newUser) {

        try {
            if (newUser.getId() != null)
                // if there is an id then update user
                return (new RequestResult(Message.UPDATED, uRepo.save(newUser)));
                // if no user with the same username found then add new one
            else if (uRepo.findByUsername(newUser.getUsername()) == null)
                return (new RequestResult(Message.ADDED, uRepo.save(newUser)));
            else {
                return (new RequestResult(Message.CONFLICT, newUser));
            }

        } catch (Exception e) {
            return new RequestResult(Message.SERVER_ERROR, newUser);
        }
    }

    @RequestMapping(value = "/users/{id}/subcribeto/{id2}", method = RequestMethod.POST)
    public
    @ResponseBody
    void
    subscribeToUser(@PathVariable("id") long subscriberId,
                    @PathVariable("id2") long subscriptionId,
                    HttpServletResponse response) {

        userHelper.subscribeUsers(subscriberId, subscriptionId);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @RequestMapping(value = "/users/{id}/unsubscribefrom/{id2}", method = RequestMethod.POST)
    public
    @ResponseBody
    void
    unsubscribeFromUser(@PathVariable("id") long subscriberId,
                        @PathVariable("id2") long subscriptionId,
                        HttpServletResponse response) {

        userHelper.removeSubscription(subscriberId, subscriptionId);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // get user subscriptions

    @RequestMapping(value="/users/{id}/subscriptions", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody Collection<User>
    getUserSubcriptions(@PathVariable("id") long userId) {

        return userHelper.getUserSubscriptionList(userId);
    }

    // get user subscriptions

    @RequestMapping(value="/users/{id}/subscribers", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody Collection<User>
    getUserSubcribers(@PathVariable("id") long userId) {

        return userHelper.getUserSubscribersList(userId);
    }

    // CHECKINS

    @RequestMapping(value="/checkins/{id}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody ResponseEntity<CheckIn>
    getCheckin(@PathVariable("id") long id, Principal p) {

        CheckIn ci = ciRepo.findOne(id);

        if (ci != null)
            return new ResponseEntity<>(ci, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/checkins/{id}/{timestamp}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody ResponseEntity<List<CheckIn>>
    getCheckins(@PathVariable("id") long id, @PathVariable("timestamp") long timestamp) {

        List<CheckIn> ci = ciRepo.findByUserId(id);

        if (ci != null)
            return new ResponseEntity<>(ci, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/checkins", method = RequestMethod.POST)
    public @ResponseBody CheckIn addCheckin(@RequestBody CheckIn newCheckin, Principal p) {

//        CheckIn ci = ciRepo.save(newCheckin);

        return checkinHelper.addCheckIn(newCheckin);
    }

    @RequestMapping(value = "/checkins/bulk", method = RequestMethod.POST)
    public @ResponseBody List<CheckIn> bulkAddCheckin(@RequestBody List<CheckIn> newCheckins) {

        List<CheckIn> checkins = new ArrayList<>();
        for (CheckIn ci : newCheckins) {
            checkins.add(ciRepo.save(ci));
        }
        return checkins;
    }

    // QUESTIONS

    @RequestMapping(value="/questions/{timestamp}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody List<Question> getQuestionsList(@PathVariable("timestamp") Long timeStampInMillis) {

        return Lists.newArrayList(qRepo.findByTimestampGreaterThan(new Timestamp(timeStampInMillis)));
    }

    // OPTIONS

    @RequestMapping(value="/options/{timestamp}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody List<Option> getOptionsList(@PathVariable("timestamp") Long timeStampInMillis) {

        return Lists.newArrayList(oRepo.findByTimestampGreaterThan(new Timestamp(timeStampInMillis)));
    }

    //RELATIONS

    @RequestMapping(value="/relations/{timestamp}/{userid}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody List<Relation> getRelationsList(@PathVariable("timestamp") Long timeStampInMillis,
                                                  @PathVariable("userid") Long userId) {

        return Lists.newArrayList(rRepo.findByTimestampGreaterThanAndUserId(
                new Timestamp(timeStampInMillis),
                userId));
    }

    @RequestMapping(value = "/relations", method = RequestMethod.POST)
    public @ResponseBody Relation addRelation(@RequestBody Relation relation) {

        return rRepo.save(relation);
    }

    @RequestMapping(value = "/relations/bulk", method = RequestMethod.POST)
    public @ResponseBody List<Relation> bulkAddRelations(@RequestBody List<Relation> relations) {

        List<Relation> rels = new ArrayList<>();
        for (Relation rel : relations) {
            rels.add(rRepo.save(rel));
        }
        return rels;
    }


}
