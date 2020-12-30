package ru.waveaccess.conference.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthImplementation extends UsernamePasswordAuthenticationToken {
    private final UserDetails userDetails;

    public AuthImplementation(Object principal, Object credentials) {
        super(principal, credentials);
        this.userDetails = (UserDetails) principal;
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }
}
