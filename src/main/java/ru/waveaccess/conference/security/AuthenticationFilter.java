package ru.waveaccess.conference.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.waveaccess.conference.dto.SignInFormDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            SignInFormDto user = objectMapper.readValue(request.getInputStream(), SignInFormDto.class);
            Authentication authentication = getAuthenticationManager()
                    .authenticate(new AuthImplementation(new UserDetailsImpl(user.getEmail(), user.getPassword()), user.getPassword()));
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return authentication;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }
}
