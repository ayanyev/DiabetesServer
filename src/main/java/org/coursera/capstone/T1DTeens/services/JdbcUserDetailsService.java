package org.coursera.capstone.T1DTeens.services;

import org.coursera.capstone.T1DTeens.entities.User;
import org.coursera.capstone.T1DTeens.entities.enums.UserType;
import org.coursera.capstone.T1DTeens.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository uRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {

            final User user = uRepo.findByUsername(username);

            UserDetails userD = new UserDetails() {

                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {

                    final String admin_roles = "role_admin, role_teen, role_parent, role_clinician";
                    final String guest_roles = "role_guest";
                    final String teen_roles = "role_teen";
                    final String parent_roles = "role_parent";
                    final String clinician_roles = "role_clinician";

                    if (user.getUserType() == UserType.ADMIN){
                        return parseRolesString(admin_roles);
                    } else  if (user.getUserType() == UserType.GUEST){
                        return parseRolesString(guest_roles);
                    } else  if (user.getUserType() == UserType.TEEN){
                        return parseRolesString(teen_roles);
                    } else  if (user.getUserType() == UserType.PARENT){
                        return parseRolesString(parent_roles);
                    } else  if (user.getUserType() == UserType.CLINICIAN) {
                        return parseRolesString(clinician_roles);
                    }

                    return null;
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }

                @Override
                public String getUsername() {
                    return user.getUsername();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return user.getEnabled();
                }
            };

            return userD;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<GrantedAuthority> parseRolesString(String roles){

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (final String role : roles.split(",")) {
            if (role != null && !"".equals(role.trim())) {
                GrantedAuthority authority = new GrantedAuthority() {
                    private static final long serialVersionUID = 3958183417696804555L;

                    public String getAuthority() {
                        return role.trim();
                    }
                };
                authorities.add(authority);
            }
        }
        return authorities;
    }
}
