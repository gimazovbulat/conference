package ru.waveaccess.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.waveaccess.conference.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "update waveaccess.users set locked = true where email = :email", nativeQuery = true)
    void lock(String email);

    Optional<User> findByConfirmLink(String link);

    List<User> findByFirstName(String firstName);
}
