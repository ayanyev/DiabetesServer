package org.coursera.capstone.T1DTeens;

import org.coursera.capstone.T1DTeens.entities.User;
import org.coursera.capstone.T1DTeens.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = T1DTeensAppApplication.class)
@WebAppConfiguration
public class T1DTeensAppApplicationTests {

	@Autowired
	UserRepository userRepo;
/*	@Autowired
	RelationRepository relRepo;*/

	@Test
	@Transactional
	public void insertUserTest() {

		User user1 = new User();
		user1.setFirstName("John");
		user1.setLastName("Doe");
		user1.setDateOfBirth(new Date());
		user1.setNickName("Dude");
		user1.setSex(UserSex.MALE);

		user1 = userRepo.save(user1);

		User user2 = new User();
		user2.setFirstName("Abraham");
		user2.setLastName("Lincoln");
		user2.setDateOfBirth(new Date());
		user2.setNickName("Dude");
		user2.setSex(UserSex.MALE);

		user2 = userRepo.save(user2);

		User user3 = new User();
		user3.setFirstName("Bill");
		user3.setLastName("Clinton");
		user3.setDateOfBirth(new Date());
		user3.setNickName("Dude");
		user3.setSex(UserSex.MALE);

		user3 = userRepo.save(user3);

		userRepo.flush();

		user1.getSubscribedToList().add(user2);
		user1.getSubscribedToList().add(user3);
		userRepo.save(user1);
		user2.getSubscribedToList().add(user3);
		userRepo.save(user2);
		user3.getSubscribedToList().add(user1);
		userRepo.save(user3);

		userRepo.flush();

		System.out.println(user1.getFirstName() + " subscribed to :");
		for (User tempP : userRepo.findOne(user1.getId()).getSubscribedToList() )
			System.out.println(" - " + tempP.getFirstName() );
		System.out.println(user1.getFirstName() + " subscribed by :");
		for (User tempP : userRepo.findOne(user1.getId()).getSubscribedByList() )
			System.out.println(" - " + tempP.getFirstName() );
		System.out.println(user2.getFirstName() + " subscribed to :");
		for (User tempP : userRepo.findOne(user2.getId()).getSubscribedToList() )
			System.out.println(" - " + tempP.getFirstName() );
		System.out.println(user2.getFirstName() + " subscribed by :");
		for (User tempP : userRepo.findOne(user2.getId()).getSubscribedByList() )
			System.out.println(" - " + tempP.getFirstName() );
		System.out.println(user3.getFirstName() + " subscribed to :");
		for (User tempP : userRepo.findOne(user3.getId()).getSubscribedToList() )
			System.out.println(" - " + tempP.getFirstName());
		System.out.println(user3.getFirstName() + " subscribed by :");
		for (User tempP : userRepo.findOne(user3.getId()).getSubscribedByList() )
			System.out.println(" - " + tempP.getFirstName() );

		user1.getSubscribedToList().remove(user3);

		User user = user1;
		user1.getSubscribedToList().clear();
		user1.getSubscribedByList().clear();

		userRepo.saveAndFlush(user1);

		user1 = user;

		userRepo.saveAndFlush(user1);


	}

}
