package ru.waveaccess.conference.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.mappers.PresentationMapper;
import ru.waveaccess.conference.mappers.UserMapper;
import ru.waveaccess.conference.mappersImpl.PresentationMapperImpl;
import ru.waveaccess.conference.mappersImpl.UserMapperImpl;
import ru.waveaccess.conference.models.Presentation;
import ru.waveaccess.conference.models.User;
import ru.waveaccess.conference.repositories.PresentationsRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PresentationServiceTest {
    @Mock
    PresentationsRepository presentationsRepository;
    UserMapper userMapper;
    PresentationMapper presentationMapper;

    @BeforeEach
    public void init() {
        userMapper = new UserMapperImpl();
        presentationMapper = new PresentationMapperImpl(userMapper);
    }

    @Test
    public void saveOrUpdateTest_SupplyPresentationDto_ReturnsPresentation() {
        Presentation presentation = Presentation.builder()
                .title("title")
                .about("about")
                .presenters(Collections.singletonList(User.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        Presentation savedPresentation = Presentation.builder()
                .id(1L)
                .title("title")
                .about("about")
                .presenters(Collections.singletonList(User.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        when(presentationsRepository.save(presentation))
                .thenReturn(savedPresentation);

        PresentationsService presentationsService = new PresentationsServiceImpl(
                presentationsRepository,
                presentationMapper,
                userMapper
        );

        PresentationDto presentationDto = PresentationDto.builder()
                .title("title")
                .about("about")
                .presenters(Collections.singletonList(UserDto.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        PresentationDto res = presentationsService.saveOrUpdate(presentationDto);

        Assertions.assertNotNull(res.getId());
    }

    @Test
    public void getByPresenterTest_SupplyPresenter_ReturnsListOfPresentationDto() {
        UserDto userDto = UserDto.builder()
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        User user = User.builder()
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        Presentation savedPresentation = Presentation.builder()
                .id(1L)
                .title("title")
                .about("about")
                .presenters(Collections.singletonList(User.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        PresentationDto expectedRes = PresentationDto.builder()
                .id(1L)
                .title("title")
                .about("about")
                .presenters(Collections.singletonList(UserDto.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        when(presentationsRepository.findByPresenters(user)).thenReturn(Collections.singletonList(savedPresentation));

        PresentationsService presentationsService = new PresentationsServiceImpl(
                presentationsRepository,
                presentationMapper,
                userMapper
        );

        List<PresentationDto> presentationsByPresenter = presentationsService.getByPresenter(userDto);

        Assertions.assertEquals(expectedRes, presentationsByPresenter.get(0));
    }
}
