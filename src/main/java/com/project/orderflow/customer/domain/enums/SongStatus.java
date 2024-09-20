package com.project.orderflow.customer.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SongStatus {

    IN_PROGRESS("신청"),
    COMPLETED("완료");

    private final String reqStatus;
}