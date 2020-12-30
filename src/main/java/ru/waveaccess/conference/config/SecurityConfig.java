package ru.waveaccess.conference.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.DelegatingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.waveaccess.conference.security.*;

import java.util.LinkedHashMap;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectMapper objectMapper;
    private final AuthenticationAttemptService authenticationAttemptService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new AuthProvider(authenticationAttemptService, userDetailsService());
        authenticationProvider.setHideUserNotFoundExceptions(true);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .formLogin()
                .loginPage("/signIn")
                .usernameParameter("email")
                .and()
                .addFilter(authenticationFilter())
                .addFilterAfter(new CrfFilterImpl(), CsrfFilter.class)
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .invalidateHttpSession(true);
    }

    @Bean("dbAuthManager")
    public AuthenticationManager authenticationManager() {
        return new AuthManager(authenticationProvider());
    }

    @Bean
    public UsernamePasswordAuthenticationFilter authenticationFilter() {
        UsernamePasswordAuthenticationFilter authenticationFilter = new AuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setAuthenticationFailureHandler(delegatingAuthHandlerImpl());
        return authenticationFilter;
    }

    @Bean("badCredentialsFailureHandler")
    public AuthenticationFailureHandler badCredentialsFailureHandler() {
        return new BadCredentialsFailureHandler(objectMapper);
    }

    @Bean("lockedUserExceptionHandler")
    public AuthenticationFailureHandler lockedUserExceptionHandler() {
        return new LockedUserExceptionHandler(objectMapper);
    }

    @Bean("delegatingAuthHandlerImpl")
    public DelegatingAuthenticationFailureHandler delegatingAuthHandlerImpl() {
        LinkedHashMap<Class<? extends AuthenticationException>, AuthenticationFailureHandler> handlers =
                new LinkedHashMap<>();
        handlers.put(BadCredentialsException.class, badCredentialsFailureHandler());
        handlers.put(LockedException.class, lockedUserExceptionHandler());
        return new DelegatingAuthenticationFailureHandler(handlers, new SimpleUrlAuthenticationFailureHandler());
    }
}

