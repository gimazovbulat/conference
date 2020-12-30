package ru.waveaccess.conference.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.dto.RoomDto;
import ru.waveaccess.conference.dto.ScheduleDto;
import ru.waveaccess.conference.mappers.RoomMapper;
import ru.waveaccess.conference.mappers.ScheduleMapper;
import ru.waveaccess.conference.models.Room;
import ru.waveaccess.conference.models.Schedule;
import ru.waveaccess.conference.repositories.SchedulesRepository;
import ru.waveaccess.conference.utils.ValidationException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SchedulesServiceImpl implements SchedulesService {
    private final SchedulesRepository schedulesRepository;
    private final RoomMapper roomMapper;
    private final ScheduleMapper scheduleMapper;
    private final PresentationsService presentationsService;
    private final RoomsService roomsService;

    @Transactional
    @Override
    public ScheduleDto create(ScheduleDto scheduleDto) {
        ZonedDateTime startTimeOfNewPres = scheduleDto.getStartTime();
        ZonedDateTime endTimeOfNewPres = scheduleDto.getEndTime();
        if (endTimeOfNewPres.isBefore(startTimeOfNewPres)) {
            throw new ValidationException("presentation start time has to be before end time");
        }

        Long presId = scheduleDto.getPresentation().getId();
        if (presId == null) {
            PresentationDto savedPresentation = presentationsService.saveOrUpdate(scheduleDto.getPresentation());
            scheduleDto.setPresentation(savedPresentation);
        }

        RoomDto roomDto = scheduleDto.getRoom();
        Room room = roomMapper.fromDto(roomDto);
        List<Schedule> schedulesByRoom = schedulesRepository.findByRoom(room);
        for (Schedule schedule : schedulesByRoom) {
            ZonedDateTime startTime = schedule.getStartTime();
            ZonedDateTime endTime = schedule.getEndTime();

            if (startTimeOfNewPres.isBefore(endTime) && startTime.isBefore(endTimeOfNewPres)) {
                throw new ValidationException("please choose another time period");
            }
        }
        Schedule savedSchedule = schedulesRepository.save(scheduleMapper.fromDto(scheduleDto));
        return scheduleMapper.toDto(savedSchedule);
    }

    @Override
    public List<ScheduleDto> findByRoomNumber(int number, int page, int size) {
        RoomDto roomDto = roomsService.findByNumber(number);
        List<Schedule> schedules = schedulesRepository
                .findByRoom(roomMapper.fromDto(roomDto), PageRequest.of(page, size));
        return schedules.stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
    }
}
