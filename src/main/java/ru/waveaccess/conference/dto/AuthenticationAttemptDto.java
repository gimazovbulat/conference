package ru.waveaccess.conference.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AuthenticationAttemptDto {
    private Long id;
    private Date lastModifiedDate;
    private String email;
    private Integer count;
}
