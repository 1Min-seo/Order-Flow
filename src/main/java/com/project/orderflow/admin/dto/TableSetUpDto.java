package com.project.orderflow.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TableSetUpDto {
    @NotNull(message = "테이블 수를 입력 해주세요")
    @Min(value = 1, message = "좌석 수는 최소 1개 이상이어야 합니다")
    private Integer numberOfSeats;

    public TableSetUpDto(Integer numberOfSeats){
        this.numberOfSeats=numberOfSeats;
    }
}
