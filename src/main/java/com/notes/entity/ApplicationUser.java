package com.notes.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ApplicationUser extends User {
    private static final long serialVersionUID = 1L;
    private final String email;
    public ApplicationUser(String username, String password, boolean enabled,
                           boolean accountNonExpired, boolean credentialsNonExpired,
                           boolean accountNonLocked,
                           Collection<GrantedAuthority> authorities,
                           String email) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
