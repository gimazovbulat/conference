package ru.waveaccess.conference.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.waveaccess.conference.dto.SignUpFormDto;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.services.UsersService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UsersService usersService;

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public UserDto signUp(@RequestBody @Valid SignUpFormDto signUpForm) {
        return usersService.signUp(signUpForm);
    }

}
