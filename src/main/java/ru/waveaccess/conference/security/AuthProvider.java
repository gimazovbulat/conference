package ru.waveaccess.conference.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.waveaccess.conference.utils.SecurityExceptions;

public class AuthProvider extends DaoAuthenticationProvider {
    private final AuthenticationAttemptService authenticationAttemptService;

    public AuthProvider(AuthenticationAttemptService authenticationAttemptService, UserDetailsService userDetailService) {
        this.authenticationAttemptService = authenticationAttemptService;
        this.authenticationAttemptService.setUserDetailsService(userDetailService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws LockedException, BadCredentialsException {
        AuthImplementation authFromFilter = (AuthImplementation) authentication;
        try {
            Authentication readyAuth = super.authenticate(authFromFilter);
            if (readyAuth != null) {
                UserDetailsImpl userDetails = (UserDetailsImpl) readyAuth.getPrincipal();
                if (userDetails.isLocked()) {
                    throw new LockedException(SecurityExceptions.LOCKED.getVal());
                }
                authenticationAttemptService.resetFailAttempts(readyAuth.getName());
                return readyAuth;
            }
        } catch (BadCredentialsException ex) {
            SecurityExceptions exception = authenticationAttemptService.updateFailAttempts(authFromFilter.getName());
            if (exception == SecurityExceptions.BAD_CREDENTIALS) {
                throw new BadCredentialsException(exception.getVal());
            } else {
                throw new LockedException(exception.getVal());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AuthProvider.class.equals(aClass);
    }
}
