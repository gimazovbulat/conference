package ru.waveaccess.conference.security;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.conference.dto.AuthenticationAttemptDto;
import ru.waveaccess.conference.mappers.AuthenticationAttemptMapper;
import ru.waveaccess.conference.models.AuthenticationAttempt;
import ru.waveaccess.conference.repositories.AuthenticationAttemptsRepository;
import ru.waveaccess.conference.utils.SecurityExceptions;


@RequiredArgsConstructor
@Service
public class AuthenticationAttemptServiceImpl implements AuthenticationAttemptService {
    private final AuthenticationAttemptsRepository authenticationAttemptsRepository;
    private final AuthenticationAttemptMapper authenticationAttemptMapper;
    private UserDetailsService userDetailsService;

    private static final int MAX_ATTEMPTS = 5;

    @Transactional
    @Override
    public SecurityExceptions updateFailAttempts(String email) {
        AuthenticationAttemptDto userAttempts = getUserAttempts(email);
        if (userAttempts == null) {
            UserDetails user = userDetailsService.loadUserByUsername(email);
            if (user != null) {
                insertNewAuthAttempt(user.getUsername(), 1);
            }
        } else {
            if (userAttempts.getCount() == MAX_ATTEMPTS) {
                UserDetailServiceImpl userDetailService = (UserDetailServiceImpl) userDetailsService;
                userDetailService.lock(email);
                return SecurityExceptions.LOCKED;
            } else {
                authenticationAttemptsRepository.updateFailAttempts(email);
            }
        }
        return SecurityExceptions.BAD_CREDENTIALS;
    }

    @Transactional
    @Override
    public void resetFailAttempts(String email) {
        authenticationAttemptsRepository.resetFailAttempts(email);
    }

    @Override
    public AuthenticationAttemptDto getUserAttempts(String email) {
        try {
            AuthenticationAttempt authenticationAttempt = authenticationAttemptsRepository.getAttempt(email);
            return authenticationAttemptMapper.toDto(authenticationAttempt);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Transactional
    @Override
    public void insertNewAuthAttempt(String email, Integer count) {
        authenticationAttemptsRepository.insertNewAuthAttempt(email, count);
    }

    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
