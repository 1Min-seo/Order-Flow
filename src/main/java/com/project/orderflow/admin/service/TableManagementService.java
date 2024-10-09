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

    public void addSeats(Owner owner, int additionalSeats) {
        TableManagement tableManagement = owner.getTableManagement();

        if (tableManagement != null) {
            addSeatsToTableManagement(tableManagement, additionalSeats);
        }
    }

    public void deleteSeats(Owner owner, int removeSeats){
        TableManagement tableManagement=owner.getTableManagement();

        if(tableManagement!=null){
            int currentSeats=tableManagement.getSeats().size();
            int newSeatCount=currentSeats-removeSeats;

            log.info("현재 좌석 개수: {}", newSeatCount);

            if(newSeatCount<0){
                throw new IllegalArgumentException("좌석은 0개 이상이어야 합니다.");
            }

            List<Seat> seats= tableManagement.getSeats();
            for(int i= currentSeats-1; i>=newSeatCount; i--){
                seatService.deleteSeat(seats.get(i));
                seats.remove(i);
            }

            tableManagement.setNumberOfSeats(newSeatCount);
            tableManagementRepository.save(tableManagement);

        }
    }

    public List<Seat> getSeatsByOwner(Owner owner) {
        TableManagement tableManagement = owner.getTableManagement();
        return tableManagement != null ? tableManagement.getSeats() : List.of();
    }


}

