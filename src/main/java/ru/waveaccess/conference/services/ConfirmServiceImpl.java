package ru.waveaccess.conference.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.mappers.UserMapper;
import ru.waveaccess.conference.models.User;
import ru.waveaccess.conference.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class ConfirmServiceImpl implements ConfirmService {
    private final UsersRepository usersrepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto confirm(String confirmLink) {
        User user = usersrepository.findByConfirmLink(confirmLink)
                .orElseThrow(() -> new EntityNotFoundException("nu such user"));
        user.setConfirmed(true);
        return userMapper.toDto(user);
    }
}
