package ru.waveaccess.conference.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "presentations")
public class Presentation extends AbstractEntity {
    @Column(updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String about;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_presentation", joinColumns = @JoinColumn(name = "presentation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> presenters;
    @OneToOne(mappedBy = "presentation", orphanRemoval = true)
    private Schedule schedule;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presentation that = (Presentation) o;
        return getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
