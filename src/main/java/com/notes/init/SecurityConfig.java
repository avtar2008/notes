package com.notes.init;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

//                .authorizeUrls()
                .antMatchers("/login", "/resources/**", "/signup.html", "/notes/**").permitAll();
                //.antMatchers("/users**","/sessions/**").hasRole("ADMIN");
//                .anyRequest().hasRole("USER")
//                .and()
//                .jee()
//                .mappableRoles("ROLE_USER","ROLE_ADMIN");
    }
}
