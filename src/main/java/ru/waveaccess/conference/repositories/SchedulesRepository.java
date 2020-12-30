package ru.waveaccess.conference.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.waveaccess.conference.models.Room;
import ru.waveaccess.conference.models.Schedule;

import java.util.List;

public interface SchedulesRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRoom(Room room, Pageable pageable);

    List<Schedule> findByRoom(Room room);
}
