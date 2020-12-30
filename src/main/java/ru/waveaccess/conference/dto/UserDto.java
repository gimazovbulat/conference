package ru.waveaccess.conference.dto;

import lombok.*;
import ru.waveaccess.conference.models.Role;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private Role role;
    private boolean locked;
    private boolean confirmed;
    private String confirmLink;
    private String firstName;
    private String lastName;
    private String middleName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return getEmail().equals(userDto.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
