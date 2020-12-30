package ru.waveaccess.conference.services;

import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.dto.UserDto;

import java.util.List;

public interface PresentationsService {
    PresentationDto saveOrUpdate(PresentationDto presentationDto);

    List<PresentationDto> getByPresenter(UserDto presenter);

    void deleteById(Long id);
}
