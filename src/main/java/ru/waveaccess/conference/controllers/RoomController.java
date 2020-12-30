package ru.waveaccess.conference.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.waveaccess.conference.dto.RoomDto;
import ru.waveaccess.conference.services.RoomsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoomController {
    private final RoomsService roomsService;

    @GetMapping("/rooms")
    public List<RoomDto> getAll(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "size", required = false) Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 15 : size;

        return roomsService.getAll(page, size);
    }
}
