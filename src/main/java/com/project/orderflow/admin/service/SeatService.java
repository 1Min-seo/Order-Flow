package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.admin.domain.TableManagement;
import com.project.orderflow.admin.repository.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public void saveSeat(Seat seat){
        seatRepository.save(seat);
    }

    public boolean activateSeat(String authCode){
        validateAuthCode(authCode);
        Seat seat = seatRepository.findByAuthCode(authCode)
                .orElseThrow(() -> new EntityNotFoundException("해당 인증 코드를 가진 좌석을 찾을 수 없습니다."));

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

    public void moveSeats(Seat seat, TableManagement newTableManagement, String newTableNumber){
        TableManagement existingTable =seat.getTableManagement();
        existingTable.getSeats().remove(seat);

        seat.setTableNumber(newTableNumber);
        seat.setTableManagement(newTableManagement);
        newTableManagement.getSeats().add(seat);
    }

//    public void mergeSeats(Seat seat1, Seat seat2){
//        TableManagement sourceSeat = seat1.getTableManagement();
//        TableManagement targetSeat = seat2.getTableManagement();
//
//        targetSeat.setTableNumber
//    }
}

