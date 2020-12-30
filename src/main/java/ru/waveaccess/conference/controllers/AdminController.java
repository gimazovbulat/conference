package ru.waveaccess.conference.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.services.AdminService;
import ru.waveaccess.conference.services.UsersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UsersService usersService;
    private final AdminService adminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/people")
    public List<UserDto> getAll(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "size", required = false) Integer size
    ) {
        page = page == null ? 0 : page;
        size = size == null ? 15 : size;

        return adminService.getAllUsers(page, size);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/people/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        adminService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/people")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return usersService.saveOrUpdate(userDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/people/{id}/role")
    public UserDto changeUsersRole(@PathVariable("id") Long id) {
        return adminService.changeUsersRole(id);
    }
}
