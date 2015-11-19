package org.coursera.capstone.T1DTeens.config;

import org.coursera.capstone.T1DTeens.services.JdbcUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;

@Configuration
@EnableAuthorizationServer
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    private ClientDetailsService clientSvc;

    private UserDetailsService userSvc;

    public AuthorizationServerConfiguration() throws Exception {

        clientSvc  = new InMemoryClientDetailsServiceBuilder()

                .withClient("mobile").authorizedGrantTypes("password", "refresh_token")
                .authorities("role_admin", "role_teen", "role_parent", "role_clinician")
                .scopes("read", "write").resourceIds("users/**", "checkins/**", "relations", "relations/**", "questions/**", "options/**")
                .accessTokenValiditySeconds(0)
                .and()

                .withClient("mobile").authorizedGrantTypes("password")
                .authorities("role_guest")
                .scopes("write").resourceIds("users", "users/bycredentials")

                //TODO change after testing done
//                .accessTokenValiditySeconds(600)
                .and()

                .build();

        userSvc = new JdbcUserDetailsService();
    }

    /**
     * Return the list of trusted client information to anyone who asks for it.
     */
    @Bean
    public ClientDetailsService clientDetailsService() throws Exception {
        return clientSvc;
    }

    /**
     * Return all of our user information to anyone in the framework who requests it.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userSvc;
    }

    /**
     * This method tells our AuthorizationServerConfigurerAdapter to use the delegated AuthenticationManager
     * to process authentication requests.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    /**
     * This method tells the AuthorizationServerConfigurerAdapter to use our self-defined client details service to
     * authenticate clients with.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

}