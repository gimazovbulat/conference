package ru.waveaccess.conference.mappers;

import org.mapstruct.Mapper;
import ru.waveaccess.conference.dto.ScheduleDto;
import ru.waveaccess.conference.models.Schedule;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, PresentationMapper.class})
public interface ScheduleMapper {
    Schedule fromDto(ScheduleDto scheduleDto);

    ScheduleDto toDto(Schedule schedule);
}
