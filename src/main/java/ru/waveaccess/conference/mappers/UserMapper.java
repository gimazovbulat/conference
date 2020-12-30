package ru.waveaccess.conference.mappers;

import org.mapstruct.Mapper;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromDto(UserDto userDto);

    UserDto toDto(User user);
}
