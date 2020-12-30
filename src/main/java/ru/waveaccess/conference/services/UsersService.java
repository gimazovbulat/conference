package ru.waveaccess.conference.services;


import ru.waveaccess.conference.dto.SignUpFormDto;
import ru.waveaccess.conference.dto.UserDto;

public interface UsersService {
    UserDto saveOrUpdate(UserDto personDto);

    UserDto signUp(SignUpFormDto signUpFormDto);

    UserDto findByEmail(String email);
}
