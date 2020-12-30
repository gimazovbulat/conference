package ru.waveaccess.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.waveaccess.conference.models.AuthenticationAttempt;

import java.util.Optional;

public interface AuthenticationAttemptsRepository extends JpaRepository<AuthenticationAttempt, Long> {
    @Modifying
    @Query(value = "update waveaccess.authentication_attempt set count = count + 1, update_time = current_timestamp " +
            "where email = :email", nativeQuery = true)
    void updateFailAttempts(String email);

    @Modifying
    @Query(value = "update waveaccess.authentication_attempt set count = 0, update_time = current_timestamp " +
            "where email = :email", nativeQuery = true)
    void resetFailAttempts(String email);

    @Query(value = "select * from waveaccess.authentication_attempt where email = :email",
            nativeQuery = true)
    AuthenticationAttempt getAttempt(String email);

    @Modifying
    @Query(value = "insert into waveaccess.authentication_attempt" +
            " (email, count, insert_time, update_time) values (:email, :count, current_timestamp, current_timestamp)",
            nativeQuery = true)
    void insertNewAuthAttempt(String email, Integer count);

    Optional<AuthenticationAttempt> findByEmail(String email);
}
