package ru.waveaccess.conference.services;


import ru.waveaccess.conference.dto.UserDto;

public interface ConfirmService {
    UserDto confirm(String confirmLink);
}
