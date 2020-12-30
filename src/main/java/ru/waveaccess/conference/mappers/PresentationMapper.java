package ru.waveaccess.conference.mappers;

import org.mapstruct.Mapper;
import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.models.Presentation;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PresentationMapper {
    Presentation fromDto(PresentationDto presentationDto);

    PresentationDto toDto(Presentation presentation);
}
