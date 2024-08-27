package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.admin.domain.TableManagement;
import com.project.orderflow.admin.repository.TableManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableManagementService {

    private final TableManagementRepository tableManagementRepository;
    private final SeatService seatService;

    public TableManagement setUpTables(Owner owner, int numberOfSeats) {
        TableManagement tableManagement = TableManagement.builder()
                .owner(owner)
                .numberOfSeats(numberOfSeats)
                .build();

        tableManagement = tableManagementRepository.save(tableManagement);
        createSeatsForTable(tableManagement);

        return tableManagement;
    }

    private void createSeatsForTable(TableManagement tableManagement) {
        for (int i = 0; i < tableManagement.getNumberOfSeats(); i++) {
            Seat seat = Seat.builder()
                    .tableNumber(String.valueOf(i + 1))
                    .authCode(generateSeatCode())
                    .tableManagement(tableManagement)
                    .isActive(false)
                    .build();
            seatService.saveSeat(seat);

            log.info("생성된 좌석: 테이블 번호 = {}, 인증 코드 = {}", seat.getTableNumber(), seat.getAuthCode());
        }
    }

    private String generateSeatCode() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }
}

