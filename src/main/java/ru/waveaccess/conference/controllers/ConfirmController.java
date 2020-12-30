package ru.waveaccess.conference.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.waveaccess.conference.services.ConfirmService;

@RequiredArgsConstructor
@RestController
public class ConfirmController {
    private final ConfirmService confirmService;

    @GetMapping("/confirm/{link}")
    public void handleRequest(@PathVariable String link) {
        confirmService.confirm(link);
    }
}
