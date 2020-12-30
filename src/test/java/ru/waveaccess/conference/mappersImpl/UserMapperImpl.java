package ru.waveaccess.conference.mappersImpl;

import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.dto.UserDto.UserDtoBuilder;
import ru.waveaccess.conference.mappers.UserMapper;
import ru.waveaccess.conference.models.User;
import ru.waveaccess.conference.models.User.UserBuilder;

public class UserMapperImpl implements UserMapper {
    public UserMapperImpl() {
    }

    public User fromDto(UserDto userDto) {
        if (userDto == null) {
            return null;
        } else {
            UserBuilder user = User.builder();
            user.id(userDto.getId());
            user.firstName(userDto.getFirstName());
            user.lastName(userDto.getLastName());
            user.email(userDto.getEmail());
            user.password(userDto.getPassword());
            user.role(userDto.getRole());
            user.locked(userDto.isLocked());
            user.confirmLink(userDto.getConfirmLink());
            user.confirmed(userDto.isConfirmed());
            return user.build();
        }
    }

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        } else {
            UserDtoBuilder userDto = UserDto.builder();
            userDto.id(user.getId());
            userDto.email(user.getEmail());
            userDto.password(user.getPassword());
            userDto.role(user.getRole());
            userDto.locked(user.isLocked());
            userDto.confirmed(user.isConfirmed());
            userDto.confirmLink(user.getConfirmLink());
            userDto.firstName(user.getFirstName());
            userDto.lastName(user.getLastName());
            return userDto.build();
        }
    }
}
