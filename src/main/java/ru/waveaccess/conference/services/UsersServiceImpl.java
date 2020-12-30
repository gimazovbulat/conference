package ru.waveaccess.conference.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.conference.dto.SignUpFormDto;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.mappers.UserMapper;
import ru.waveaccess.conference.models.Role;
import ru.waveaccess.conference.models.User;
import ru.waveaccess.conference.repositories.UsersRepository;
import ru.waveaccess.conference.utils.MailType;
import ru.waveaccess.conference.utils.SecurityExceptions;
import ru.waveaccess.conference.utils.SendEmail;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto saveOrUpdate(UserDto userDto) {
        User user = userMapper.fromDto(userDto);
        User savedPerson = usersRepository.save(user);
        return userMapper.toDto(savedPerson);
    }

    @SendEmail(mailType = MailType.CONFIRMATION)
    @Transactional
    @Override
    public UserDto signUp(SignUpFormDto signUpFormDto) {
        usersRepository.findByEmail(signUpFormDto.getEmail())
                .ifPresent((user) -> {
                    throw new BadCredentialsException(SecurityExceptions.BAD_CREDENTIALS.getVal());
                });
        User user = User.builder()
                .role(Role.LISTENER)
                .email(signUpFormDto.getEmail())
                .firstName(signUpFormDto.getFirstName())
                .lastName(signUpFormDto.getLastName())
                .password(passwordEncoder.encode(signUpFormDto.getPassword()))
                .confirmLink(UUID.randomUUID().toString())
                .confirmed(false)
                .locked(false)
                .build();

        UserDto userDto = userMapper.toDto(user);
        return saveOrUpdate(userDto);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("user doesn't exist"));

        return userMapper.toDto(user);
    }
}
