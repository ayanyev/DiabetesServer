package org.coursera.capstone.T1DTeens.repositories;

import org.coursera.capstone.T1DTeens.entities.User;
import org.coursera.capstone.T1DTeens.entities.enums.UserType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.transaction.Transactional;
import java.util.Collection;

//@PreAuthorize("hasRole('ROLE_USER')")
//@RepositoryRestResource(collectionResourceRel = "users",
//                        path = "users")
//@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void delete(Long aLong);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void delete(User entity);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void delete(Iterable<? extends User> entities);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void deleteAll();

    User findByUsername(String username);

    Collection<User> findByUserType(UserType userType);

    User findByPassword(String password);
}
