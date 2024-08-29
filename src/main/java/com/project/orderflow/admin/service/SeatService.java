package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.admin.domain.TableManagement;
import com.project.orderflow.admin.repository.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public void saveSeat(Seat seat){
        seatRepository.save(seat);
    }

    public boolean activateSeat(String tableNumber, String authCode){
        validateAuthCode(authCode);
        Seat seat = seatRepository.findByTableNumberAndAuthCode(tableNumber, authCode)
                .orElseThrow(() -> new EntityNotFoundException(tableNumber + "번 테이블을 찾을 수 없습니다."));

        boolean isActivated = seat.activateSeat(authCode);
        if (isActivated) {
            saveSeat(seat);
        }

        return isActivated;
    }

    public void validateAuthCode(String authCode){
        if(authCode == null || authCode.isEmpty()){
            throw new IllegalStateException("유효하지 않는 인증코드 입니다.");
        }
    }

    public void deleteSeat(Seat seat){
        seatRepository.delete(seat);
    }
}

