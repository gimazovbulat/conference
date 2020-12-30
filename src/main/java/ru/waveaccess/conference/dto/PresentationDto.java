package ru.waveaccess.conference.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class PresentationDto {
    private Long id;
    @NotNull(message = "title can't be empty")
    private String title;
    @NotNull(message = "please describe your presentation in few word")
    private String about;
    private List<UserDto> presenters;
}
