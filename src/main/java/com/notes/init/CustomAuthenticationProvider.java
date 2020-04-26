package com.notes.init;

import com.google.common.collect.Lists;
import com.notes.entity.ApplicationUser;
import com.notes.utils.MongoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        Authentication auth = null;
        String role = MongoUtils.getApplicationRole(userName, password);
        if (role != null) {
            Collection<GrantedAuthority> grantedAuths = Lists.newArrayList(new SimpleGrantedAuthority(role.trim()));
            ApplicationUser appUser = new ApplicationUser(userName, password, true, true, true, true, grantedAuths, "TestEmail");
            auth = new UsernamePasswordAuthenticationToken(userName, password, Collections.emptyList());
            return auth;
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}