package ru.waveaccess.conference.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.mappers.UserMapper;
import ru.waveaccess.conference.models.User;
import ru.waveaccess.conference.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.waveaccess.conference.models.Role.LISTENER;
import static ru.waveaccess.conference.models.Role.PRESENTER;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto changeUsersRole(Long id) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user with such id doesn't exist"));

        if (user.getRole() == LISTENER) {
            user.setRole(PRESENTER);
        } else {
            user.setRole(LISTENER);
        }
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        usersRepository.delete(
                usersRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("user with such id doesn't exist")));
    }

    @Override
    public List<UserDto> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return usersRepository.findAll(pageRequest).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
