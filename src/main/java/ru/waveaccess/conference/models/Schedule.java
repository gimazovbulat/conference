package ru.waveaccess.conference.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
public class Schedule extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "presentation_id")
    private Presentation presentation;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "start_time")
    private ZonedDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return getRoom().equals(schedule.getRoom()) && getPresentation().equals(schedule.getPresentation()) &&
                getStartTime().equals(schedule.getStartTime()) &&
                getEndTime().equals(schedule.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoom(), getPresentation(), getStartTime(), getEndTime());
    }
}
