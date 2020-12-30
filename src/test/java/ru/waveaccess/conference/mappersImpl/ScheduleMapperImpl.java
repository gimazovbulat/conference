package ru.waveaccess.conference.mappersImpl;

import lombok.RequiredArgsConstructor;
import ru.waveaccess.conference.dto.ScheduleDto;
import ru.waveaccess.conference.mappers.PresentationMapper;
import ru.waveaccess.conference.mappers.RoomMapper;
import ru.waveaccess.conference.mappers.ScheduleMapper;
import ru.waveaccess.conference.models.Schedule;

@RequiredArgsConstructor
public class ScheduleMapperImpl implements ScheduleMapper {
    private final RoomMapper roomMapper;
    private final PresentationMapper presentationMapper;

    public Schedule fromDto(ScheduleDto scheduleDto) {
        if (scheduleDto == null) {
            return null;
        } else {
            Schedule.ScheduleBuilder schedule = Schedule.builder();
            schedule.id(scheduleDto.getId());
            schedule.room(this.roomMapper.fromDto(scheduleDto.getRoom()));
            schedule.presentation(this.presentationMapper.fromDto(scheduleDto.getPresentation()));
            schedule.startTime(scheduleDto.getStartTime());
            schedule.endTime(scheduleDto.getEndTime());
            return schedule.build();
        }
    }

    public ScheduleDto toDto(Schedule schedule) {
        if (schedule == null) {
            return null;
        } else {
            ScheduleDto.ScheduleDtoBuilder scheduleDto = ScheduleDto.builder();
            scheduleDto.id(schedule.getId());
            scheduleDto.room(this.roomMapper.toDto(schedule.getRoom()));
            scheduleDto.presentation(this.presentationMapper.toDto(schedule.getPresentation()));
            scheduleDto.startTime(schedule.getStartTime());
            scheduleDto.endTime(schedule.getEndTime());
            return scheduleDto.build();
        }
    }
}
