package ru.waveaccess.conference.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.dto.RoomDto;
import ru.waveaccess.conference.dto.ScheduleDto;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.mappers.PresentationMapper;
import ru.waveaccess.conference.mappers.RoomMapper;
import ru.waveaccess.conference.mappers.ScheduleMapper;
import ru.waveaccess.conference.mappers.UserMapperImpl;
import ru.waveaccess.conference.mappersImpl.PresentationMapperImpl;
import ru.waveaccess.conference.mappersImpl.RoomMapperImpl;
import ru.waveaccess.conference.mappersImpl.ScheduleMapperImpl;
import ru.waveaccess.conference.models.Presentation;
import ru.waveaccess.conference.models.Room;
import ru.waveaccess.conference.models.Schedule;
import ru.waveaccess.conference.models.User;
import ru.waveaccess.conference.repositories.SchedulesRepository;
import ru.waveaccess.conference.utils.ValidationException;

import java.time.ZonedDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {
    @Mock
    SchedulesRepository schedulesRepository;
    RoomMapper roomMapper;
    ScheduleMapper scheduleMapper;
    @Mock
    PresentationsService presentationsService;
    @Mock
    RoomsService roomsService;

    @BeforeEach
    public void init() {
        roomMapper = new RoomMapperImpl();
        PresentationMapper presentationMapper = new PresentationMapperImpl(new UserMapperImpl());
        scheduleMapper = new ScheduleMapperImpl(roomMapper, presentationMapper);
    }

    @Test
    public void createTest_SupplyScheduleDto_ReturnsSavedScheduleDto() {
        PresentationDto presentation = PresentationDto.builder()
                .title("title2")
                .about("about2")
                .presenters(Collections.singletonList(UserDto.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        PresentationDto savedPresentation = PresentationDto.builder()
                .id(2L)
                .title("title2")
                .about("about2")
                .presenters(Collections.singletonList(UserDto.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        when(presentationsService.saveOrUpdate(presentation)).thenReturn(savedPresentation);

        Room room = Room.builder()
                .number(1)
                .id(1L)
                .build();

        ZonedDateTime startTime = ZonedDateTime.parse("2020-11-16T00:44:00.032686+03:00");
        ZonedDateTime endTime = ZonedDateTime.parse("2020-11-16T00:45:00.032686+03:00");

        Schedule schedule = Schedule.builder()
                .id(1L)
                .startTime(startTime)
                .endTime(endTime)
                .room(room)
                .presentation(
                        Presentation.builder().title("title")
                                .about("about")
                                .presenters(Collections.singletonList(User.builder()
                                        .email("email")
                                        .firstName("firstName")
                                        .lastName("lastName")
                                        .build()))
                                .build())
                .build();

        when(schedulesRepository.findByRoom(room)).thenReturn(Collections.singletonList(schedule));

        ZonedDateTime startTime2 = ZonedDateTime.parse("2020-11-16T00:46:00.032686+03:00");
        ZonedDateTime endTime2 = ZonedDateTime.parse("2020-11-16T00:47:00.032686+03:00");

        RoomDto roomDto = RoomDto.builder()
                .number(1)
                .id(1L)
                .build();

        ScheduleDto scheduleDtoToSave = ScheduleDto.builder()
                .startTime(startTime2)
                .endTime(endTime2)
                .room(roomDto)
                .presentation(
                        PresentationDto.builder().title("title2")
                                .about("about2")
                                .presenters(Collections.singletonList(UserDto.builder()
                                        .email("email")
                                        .firstName("firstName")
                                        .lastName("lastName")
                                        .build()))
                                .build())
                .build();

        Schedule scheduleToSave = Schedule.builder()
                .startTime(startTime2)
                .endTime(endTime2)
                .room(room)
                .presentation(
                        Presentation.builder()
                                .title("title2")
                                .about("about2")
                                .presenters(Collections.singletonList(User.builder()
                                        .email("email")
                                        .firstName("firstName")
                                        .lastName("lastName")
                                        .build()))
                                .build())
                .build();

        Schedule savedSchedule = Schedule.builder()
                .id(2L)
                .startTime(startTime2)
                .endTime(endTime2)
                .room(room)
                .presentation(
                        Presentation.builder().title("title2")
                                .id(2L)
                                .about("about2")
                                .presenters(Collections.singletonList(User.builder()
                                        .email("email")
                                        .firstName("firstName")
                                        .lastName("lastName")
                                        .build()))
                                .build())
                .build();


        when(schedulesRepository.save(scheduleToSave)).thenReturn(savedSchedule);

        SchedulesService schedulesService = new SchedulesServiceImpl(
                schedulesRepository,
                roomMapper,
                scheduleMapper,
                presentationsService,
                roomsService
        );

        ScheduleDto res = schedulesService.create(scheduleDtoToSave);
        Assertions.assertNotNull(res.getId());
        Assertions.assertNotNull(res.getPresentation().getId());
    }

    @Test
    public void createTest_SupplyScheduleDto_ThrowsValidationExceptionEndTimeBeforeStartTime() {
        ZonedDateTime startTime = ZonedDateTime.parse("2020-11-16T00:47:00.032686+03:00");
        ZonedDateTime endTime = ZonedDateTime.parse("2020-11-16T00:46:00.032686+03:00");

        ScheduleDto scheduleDtoToSave = ScheduleDto.builder()
                .startTime(startTime)
                .endTime(endTime)
                .room(RoomDto.builder()
                        .number(1)
                        .id(1L)
                        .build())
                .presentation(
                        PresentationDto.builder().title("title2")
                                .about("about2")
                                .presenters(Collections.singletonList(UserDto.builder()
                                        .email("email")
                                        .firstName("firstName")
                                        .lastName("lastName")
                                        .build()))
                                .build())
                .build();

        SchedulesService schedulesService = new SchedulesServiceImpl(
                schedulesRepository,
                roomMapper,
                scheduleMapper,
                presentationsService,
                roomsService
        );

        assertThrows(ValidationException.class, () -> schedulesService.create(scheduleDtoToSave), "presentation start time has to be before end time");
    }

    @Test
    public void createTest_SupplyScheduleDto_ThrowsValidationExceptionBecauseOfOverlapping() {
        PresentationDto presentation = PresentationDto.builder()
                .title("title2")
                .about("about2")
                .presenters(Collections.singletonList(UserDto.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        PresentationDto savedPresentation = PresentationDto.builder()
                .id(2L)
                .title("title2")
                .about("about2")
                .presenters(Collections.singletonList(UserDto.builder()
                        .email("email")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build()))
                .build();

        when(presentationsService.saveOrUpdate(presentation)).thenReturn(savedPresentation);

        Room room = Room.builder()
                .number(1)
                .id(1L)
                .build();

        ZonedDateTime startTime = ZonedDateTime.parse("2020-11-16T00:44:00.032686+03:00");
        ZonedDateTime endTime = ZonedDateTime.parse("2020-11-16T00:47:00.032686+03:00");

        Schedule schedule = Schedule.builder()
                .id(1L)
                .startTime(startTime)
                .endTime(endTime)
                .room(room)
                .presentation(
                        Presentation.builder().title("title")
                                .about("about")
                                .presenters(Collections.singletonList(User.builder()
                                        .email("email")
                                        .firstName("firstName")
                                        .lastName("lastName")
                                        .build()))
                                .build())
                .build();

        when(schedulesRepository.findByRoom(room)).thenReturn(Collections.singletonList(schedule));

        ZonedDateTime startTime2 = ZonedDateTime.parse("2020-11-16T00:46:00.032686+03:00");
        ZonedDateTime endTime2 = ZonedDateTime.parse("2020-11-16T00:47:00.032686+03:00");

        RoomDto roomDto = RoomDto.builder()
                .number(1)
                .id(1L)
                .build();

        ScheduleDto scheduleDtoToSave = ScheduleDto.builder()
                .startTime(startTime2)
                .endTime(endTime2)
                .room(roomDto)
                .presentation(
                        PresentationDto.builder().title("title2")
                                .about("about2")
                                .presenters(Collections.singletonList(UserDto.builder()
                                        .email("email")
                                        .firstName("firstName")
                                        .lastName("lastName")
                                        .build()))
                                .build())
                .build();

        SchedulesService schedulesService = new SchedulesServiceImpl(
                schedulesRepository,
                roomMapper,
                scheduleMapper,
                presentationsService,
                roomsService
        );
        assertThrows(ValidationException.class, () -> schedulesService.create(scheduleDtoToSave), "please choose another time period");
    }
}
