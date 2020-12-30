package ru.waveaccess.conference.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.waveaccess.conference.dto.AuthenticationAttemptDto;
import ru.waveaccess.conference.utils.SecurityExceptions;

public interface AuthenticationAttemptService {
    SecurityExceptions updateFailAttempts(String email);

    void resetFailAttempts(String email);

    AuthenticationAttemptDto getUserAttempts(String email);

    void insertNewAuthAttempt(String email, Integer count);

    void setUserDetailsService(UserDetailsService userDetailsService);
}
