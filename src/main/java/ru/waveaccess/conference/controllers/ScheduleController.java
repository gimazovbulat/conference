package ru.waveaccess.conference.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.waveaccess.conference.dto.ScheduleDto;
import ru.waveaccess.conference.services.SchedulesService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ScheduleController {
    private final SchedulesService schedulesService;

    @PreAuthorize("hasAuthority('PRESENTER')")
    @PostMapping("/schedules")
    public ScheduleDto create(@RequestBody @Valid ScheduleDto scheduleDto) {
        return schedulesService.create(scheduleDto);
    }

    @GetMapping("/schedules/rooms/{number}")
    public List<ScheduleDto> getByRoom(@PathVariable("number") @Positive Integer number,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "size", required = false) Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 15 : size;
        return schedulesService.findByRoomNumber(number, page, size);
    }
}
