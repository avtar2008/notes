package com.notes.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers( "/notes/**");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//
////                .authorizeUrls()
//                .antMatchers("/login", "/signup.html").permitAll()
////                .antMatchers("/users**","/sessions/**").hasRole("ADMIN")
//                .anyRequest().hasRole("USER")
//                .and()
//                .jee()
//                .mappableRoles("ROLE_USER","ROLE_ADMIN");
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/error.html").permitAll()
                .and()
                .authorizeRequests().antMatchers("/static/**").authenticated()
                .and()
                .formLogin()
//                .loginPage("/login.html")
                .successForwardUrl("/home.html")
                .loginProcessingUrl("/notes/do_login")
                .failureForwardUrl("/error.html")
                .defaultSuccessUrl("/create.html")
                .permitAll().and().authenticationProvider(new CustomAuthenticationProvider());
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("user")).roles("USER")
                .and()
                .withUser("a").password(passwordEncoder().encode("a")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
