package ru.waveaccess.conference.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.security.CurrentUser;
import ru.waveaccess.conference.services.PresentationsService;
import ru.waveaccess.conference.services.UsersService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PresentationController {
    private final PresentationsService presentationsService;
    private final UsersService usersService;

    @PreAuthorize("hasAnyAuthority('PRESENTER')")
    @PostMapping("/presentations")
    public PresentationDto createPresentation(@RequestBody @Valid PresentationDto presentationDto,
                                              @CurrentUser UserDetails userDetails) {
        if (presentationDto.getPresenters() == null || presentationDto.getPresenters().isEmpty()) {
            String email = userDetails.getUsername();
            UserDto userDto = usersService.findByEmail(email);
            List<UserDto> presenters = new ArrayList<>();
            presenters.add(userDto);
            presentationDto.setPresenters(presenters);
        }
        return presentationsService.saveOrUpdate(presentationDto);
    }

    @PreAuthorize("hasAnyAuthority('PRESENTER')")
    @GetMapping("/presentations")
    public List<PresentationDto> getAllByPresenter(@CurrentUser UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserDto userDto = usersService.findByEmail(email);
        return presentationsService.getByPresenter(userDto);
    }

    @PreAuthorize("hasAuthority('PRESENTER')")
    @PutMapping("/presentations")
    public PresentationDto update(@RequestBody @Valid PresentationDto presentationDto) {
        return presentationsService.saveOrUpdate(presentationDto);
    }

    @PreAuthorize("hasAuthority('PRESENTER')")
    @DeleteMapping("/presentations/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        presentationsService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
