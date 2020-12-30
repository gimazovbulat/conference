package ru.waveaccess.conference.services;

import ru.waveaccess.conference.dto.ScheduleDto;

import java.util.List;

public interface SchedulesService {
    ScheduleDto create(ScheduleDto scheduleDto);

    List<ScheduleDto> findByRoomNumber(int number, int page, int size);
}
