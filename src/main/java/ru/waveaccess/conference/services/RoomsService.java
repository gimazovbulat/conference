package ru.waveaccess.conference.services;

import ru.waveaccess.conference.dto.RoomDto;

import java.util.List;

public interface RoomsService {
    RoomDto findById(Long id);

    RoomDto findByNumber(int number);

    List<RoomDto> getAll(int page, int size);
}
