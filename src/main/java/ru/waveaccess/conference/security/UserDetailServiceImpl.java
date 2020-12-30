package ru.waveaccess.conference.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.conference.models.User;
import ru.waveaccess.conference.repositories.UsersRepository;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new UserDetailsImpl(user.getEmail(), user.getPassword(), user.getRole(), user.isLocked(), user.isConfirmed());
    }

    @Transactional
    public void lock(String email) {
        usersRepository.lock(email);
    }
}
