package ru.waveaccess.conference.mappers;

import org.mapstruct.Mapper;
import ru.waveaccess.conference.dto.AuthenticationAttemptDto;
import ru.waveaccess.conference.models.AuthenticationAttempt;

@Mapper(componentModel = "spring")
public interface AuthenticationAttemptMapper {
    AuthenticationAttempt fromDto(AuthenticationAttemptDto dto);

    AuthenticationAttemptDto toDto(AuthenticationAttempt model);
}
