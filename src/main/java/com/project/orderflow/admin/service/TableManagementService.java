package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.admin.domain.TableManagement;
import com.project.orderflow.admin.repository.OwnerRepository;
import com.project.orderflow.admin.repository.SeatRepository;
import com.project.orderflow.admin.repository.TableManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableManagementService {

    private final TableManagementRepository tableManagementRepository;
    private final SeatService seatService;
    private final OwnerRepository ownerRepository;
    private final SeatRepository seatRepository;

    @Value("${qr.code.api}")
    private String qrCodeApiUrl;

    public TableManagement setUpTables(Owner owner, int numberOfSeats) {
        Owner findOwner = ownerRepository.findById(owner.getId()).orElseThrow();
        if(findOwner!=null){
            TableManagement tableManagement = TableManagement.builder()
                    .owner(owner)
                    .numberOfSeats(numberOfSeats)
                    .build();

            tableManagement = tableManagementRepository.save(tableManagement);
            addSeatsToTableManagement(tableManagement, numberOfSeats);

            return tableManagement;
        }
        return null;
//        return ownerRepository.findById(owner.getId())
//                .filter(Owner::getIsActive)
//                .map(findOwner->{
//                    TableManagement tableManagement=TableManagement.builder()
//                            .owner(findOwner)
//                            .numberOfSeats(numberOfSeats)
//                            .build();
//
//                    tableManagement=tableManagementRepository.save(tableManagement);
//                    addSeatsToTableManagement(tableManagement, numberOfSeats);
//                    return tableManagement;
//                });
    }

    private void addSeatsToTableManagement(TableManagement tableManagement, int numberOfSeats) {
        int currentSeats=tableManagement.getSeats().size();
        for (int i = currentSeats; i < currentSeats+numberOfSeats; i++) {
            String authcode = generateSeatCode();
            Seat seat = Seat.builder()
                    .tableNumber(String.valueOf(i + 1))
                    .authCode(authcode)
                    .tableManagement(tableManagement)
                    .isActive(false)
                    .qrUrl(qrCodeApiUrl + authcode)
                    .build();
            seatService.saveSeat(seat);
            tableManagement.getSeats().add(seat);
        }
        tableManagementRepository.save(tableManagement);
    }

    private String generateSeatCode() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    // 좌석을 추가할 때 x, y 좌표를 포함하는 메서드
    public void addSeat(Owner owner, Double x, Double y) {
        TableManagement tableManagement = owner.getTableManagement();

        if (tableManagement != null) {
            String authcode = generateSeatCode();
            int currentSeatCount = tableManagement.getSeats().size();

            Seat seat = Seat.builder()
                    .tableNumber(String.valueOf(currentSeatCount + 1))
                    .authCode(authcode)
                    .tableManagement(tableManagement)
                    .isActive(false)
                    .qrUrl(qrCodeApiUrl + authcode)
                    .x(x)
                    .y(y)
                    .build();

            seatService.saveSeat(seat);
            tableManagement.getSeats().add(seat);

            // 좌석 개수 업데이트
            tableManagement.setNumberOfSeats(tableManagement.getSeats().size());
            tableManagementRepository.save(tableManagement);
        }
    }


    // 좌석 삭제 메서드
    public void deleteSeat(Long ownerId, Long seatId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 점주를 찾을 수 없습니다."));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("해당 좌석을 찾을 수 없습니다."));

        // 좌석이 해당 점주의 테이블 관리에 속하는지 확인
        if (!seat.getTableManagement().getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException("해당 좌석은 이 점주에게 속하지 않습니다.");
        }

        // 좌석 삭제
        seatRepository.delete(seat);

        // 테이블 관리 좌석 수 업데이트
        TableManagement tableManagement = seat.getTableManagement();
        tableManagement.getSeats().remove(seat);
        tableManagement.setNumberOfSeats(tableManagement.getSeats().size());
        tableManagementRepository.save(tableManagement);

        log.info("좌석 {}이 삭제되었습니다.", seatId);
    }

    public List<Seat> getSeatsByOwner(Owner owner) {
        TableManagement tableManagement = owner.getTableManagement();
        return tableManagement != null ? tableManagement.getSeats() : List.of();
    }

    // 좌석의 x, y 좌표를 변경하는 메서드
    public void moveSeat(Long ownerId, Long seatId, Double x, Double y) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 점주를 찾을 수 없습니다."));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("해당 좌석을 찾을 수 없습니다."));

        // 테이블 관리가 해당 점주의 것인지 확인 (옵션)
        if (!seat.getTableManagement().getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException("해당 좌석은 이 점주에게 속하지 않습니다.");
        }

        // x, y 좌표 변경
        seat.setX(x);
        seat.setY(y);

        // 변경 사항 저장
        seatRepository.save(seat);

        log.info("좌석 {}의 위치가 x={}, y={}로 변경되었습니다.", seatId, x, y);
    }


}

