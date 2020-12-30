package ru.waveaccess.conference.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.waveaccess.conference.dto.RoomDto;
import ru.waveaccess.conference.mappers.RoomMapper;
import ru.waveaccess.conference.models.Room;
import ru.waveaccess.conference.repositories.RoomsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomsServiceImpl implements RoomsService {
    private final RoomsRepository roomsRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomDto findById(Long id) {
        Room room = roomsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user doesn't exist"));
        return roomMapper.toDto(room);
    }

    @Override
    public RoomDto findByNumber(int number) {
        Room room = roomsRepository.findByNumber(number)
                .orElseThrow(() -> new EntityNotFoundException("user doesn't exist"));
        return roomMapper.toDto(room);
    }

    @Override
    public List<RoomDto> getAll(int page, int size) {
        List<Room> allRooms = roomsRepository.findAll(PageRequest.of(page, size)).getContent();
        return allRooms.stream().map(roomMapper::toDto).collect(Collectors.toList());
    }
}
