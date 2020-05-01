package com.notes.init;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notes.entity.ApplicationUser;
import com.notes.entity.UserDetails;
import com.notes.utils.MongoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    MongoDatabase mongoDatabase;


    private static Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        Authentication auth = null;
        MongoCollection<UserDetails> userCollection = mongoDatabase.getCollection("users", UserDetails.class);
        FindIterable result = userCollection.find(MongoUtils.createUserFilter(userName, password));

        java.util.List<UserDetails> userList = MongoUtils.mapUserIterableToList(result);

        if (userList.isEmpty()) {
            return null;
        } else {
            Collection<GrantedAuthority> grantedAuths = Lists.newArrayList(new SimpleGrantedAuthority(userList.get(0).getRole()));
            auth = new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
            return auth;
        }
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}