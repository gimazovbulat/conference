package ru.waveaccess.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.waveaccess.conference.models.Presentation;
import ru.waveaccess.conference.models.User;

import java.util.List;

public interface PresentationsRepository extends JpaRepository<Presentation, Long> {
    List<Presentation> findByPresenters(User presenter);
}
