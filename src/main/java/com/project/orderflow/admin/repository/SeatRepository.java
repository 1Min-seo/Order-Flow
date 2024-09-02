package com.project.orderflow.admin.repository;

import com.project.orderflow.admin.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByTableNumberAndAuthCode(String tableNumber, String authCode);

    // 테이블 번호로 좌석을 찾는 메서드
    Optional<Seat> findByTableNumber(String tableNumber);
}
