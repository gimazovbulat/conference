package ru.waveaccess.conference.mappers;

import org.mapstruct.Mapper;
import ru.waveaccess.conference.dto.RoomDto;
import ru.waveaccess.conference.models.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room fromDto(RoomDto roomDto);

    RoomDto toDto(Room room);
}
