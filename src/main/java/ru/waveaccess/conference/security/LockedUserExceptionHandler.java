package ru.waveaccess.conference.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import ru.waveaccess.conference.utils.SecurityExceptions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class LockedUserExceptionHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.LOCKED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("error", SecurityExceptions.LOCKED.getVal());

        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}
