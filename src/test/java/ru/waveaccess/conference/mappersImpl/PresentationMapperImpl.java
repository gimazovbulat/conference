package ru.waveaccess.conference.mappersImpl;

import lombok.RequiredArgsConstructor;
import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.mappers.PresentationMapper;
import ru.waveaccess.conference.mappers.UserMapper;
import ru.waveaccess.conference.models.Presentation;
import ru.waveaccess.conference.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class PresentationMapperImpl implements PresentationMapper {
    private final UserMapper userMapper;

    public Presentation fromDto(PresentationDto presentationDto) {
        if (presentationDto == null) {
            return null;
        } else {
            Presentation.PresentationBuilder presentation = Presentation.builder();
            presentation.id(presentationDto.getId());
            presentation.title(presentationDto.getTitle());
            presentation.about(presentationDto.getAbout());
            presentation.presenters(this.userDtoListToUserList(presentationDto.getPresenters()));
            return presentation.build();
        }
    }

    public PresentationDto toDto(Presentation presentation) {
        if (presentation == null) {
            return null;
        } else {
            PresentationDto.PresentationDtoBuilder presentationDto = PresentationDto.builder();
            presentationDto.id(presentation.getId());
            presentationDto.title(presentation.getTitle());
            presentationDto.about(presentation.getAbout());
            presentationDto.presenters(this.userListToUserDtoList(presentation.getPresenters()));
            return presentationDto.build();
        }
    }

    protected List<User> userDtoListToUserList(List<UserDto> list) {
        if (list == null) {
            return null;
        } else {
            List<User> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                UserDto userDto = (UserDto) var3.next();
                list1.add(this.userMapper.fromDto(userDto));
            }

            return list1;
        }
    }

    protected List<UserDto> userListToUserDtoList(List<User> list) {
        if (list == null) {
            return null;
        } else {
            List<UserDto> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                User user = (User) var3.next();
                list1.add(this.userMapper.toDto(user));
            }

            return list1;
        }
    }
}
