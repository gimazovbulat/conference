package ru.waveaccess.conference.mappersImpl;

import ru.waveaccess.conference.dto.RoomDto;
import ru.waveaccess.conference.mappers.RoomMapper;
import ru.waveaccess.conference.models.Room;

public class RoomMapperImpl implements RoomMapper {
    public RoomMapperImpl() {
    }

    public Room fromDto(RoomDto roomDto) {
        if (roomDto == null) {
            return null;
        } else {
            Room.RoomBuilder room = Room.builder();
            room.id(roomDto.getId());
            room.number(roomDto.getNumber());
            return room.build();
        }
    }

    public RoomDto toDto(Room room) {
        if (room == null) {
            return null;
        } else {
            RoomDto.RoomDtoBuilder roomDto = RoomDto.builder();
            roomDto.id(room.getId());
            roomDto.number(room.getNumber());
            return roomDto.build();
        }
    }
}
