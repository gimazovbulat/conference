package ru.waveaccess.conference.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class RoomDto {
    private Long id;
    private int number;
}
