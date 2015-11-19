package org.coursera.capstone.T1DTeens.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends
        ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("users");
    }

    // This method configures the OAuth scopes required by clients to access
    // all of the paths in the video service.
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // access fot token

        http
                .authorizeRequests()
                .antMatchers("/oauth/token").anonymous();

        // used to register new user only by admin and guest

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users", "/users/bycredentials")
                .access("#oauth2.clientHasAnyRole('role_guest, role_admin')");

        // used to get any type of resource

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**")
                .access("#oauth2.hasAnyScope('read, write')");


        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**")
                .access("#oauth2.hasScope('write')");
    }
}
