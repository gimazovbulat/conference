package ru.waveaccess.conference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDto {
    @Email
    @NotNull(message = "email can't be empty")
    @NotEmpty(message = "email can't be empty")
    private String email;
    @NotNull(message = "password can't be empty")
    @NotEmpty(message = "email can't be empty")    private String password;
    @NotNull(message = "first name can't be empty")
    private String firstName;
    @NotNull(message = "last name can't be empty")
    private String lastName;
}
