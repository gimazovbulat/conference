package ru.waveaccess.conference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
public class SignInFormDto {
    @NotNull(message = "email can't be empty")
    @Email
    @NotEmpty(message = "email can't be empty")
    private String email;
    @NotNull(message = "email can't be empty")
    @NotEmpty(message = "password can't be empty")
    private String password;
}
