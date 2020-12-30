package ru.waveaccess.conference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private Long id;
    private RoomDto room;
    @Valid
    private PresentationDto presentation;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
