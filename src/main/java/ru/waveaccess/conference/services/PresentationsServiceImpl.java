package ru.waveaccess.conference.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.mappers.PresentationMapper;
import ru.waveaccess.conference.mappers.UserMapper;
import ru.waveaccess.conference.models.Presentation;
import ru.waveaccess.conference.models.User;
import ru.waveaccess.conference.repositories.PresentationsRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PresentationsServiceImpl implements PresentationsService {
    private final PresentationsRepository presentationsRepository;
    private final PresentationMapper presentationMapper;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public PresentationDto saveOrUpdate(PresentationDto presentationDto) {
        Presentation presentation = presentationMapper.fromDto(presentationDto);
        Presentation savedPresentation = presentationsRepository.save(presentation);
        return presentationMapper.toDto(savedPresentation);
    }

    @Override
    public List<PresentationDto> getByPresenter(UserDto presenterDto) {
        User presenter = userMapper.fromDto(presenterDto);
        List<Presentation> presentationsByPresenter = presentationsRepository.findByPresenters(presenter);
        return presentationsByPresenter.stream()
                .map(presentationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        presentationsRepository.deleteById(id);
    }
}
