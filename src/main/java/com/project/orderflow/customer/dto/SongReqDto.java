package com.project.orderflow.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.orderflow.customer.domain.enums.SongStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SongReqDto {

    private Long ownerId;

    private String tableNumber;

    private String title;

    private String artist;

    private SongStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime requestedAt;

}