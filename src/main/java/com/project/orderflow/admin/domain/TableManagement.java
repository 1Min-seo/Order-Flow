package com.project.orderflow.admin.domain;

import com.project.orderflow.admin.service.SeatService;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@NoArgsConstructor
@Getter
public class TableManagement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int numberOfSeats;

    @OneToMany(mappedBy = "tableManagement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Seat> seats=new ArrayList<>();

    @OneToOne
    @JoinColumn(name="owner_id", nullable = false)
    private Owner owner;


    @Builder
    public TableManagement(Owner owner, int numberOfSeats) {
        this.owner=owner;
        this.numberOfSeats = numberOfSeats;
        owner.setTableManagement(this);
    }

    public void createSeats(SeatService seatService) {
        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = Seat.builder()
                    .tableNumber(String.valueOf(i + 1))
                    .authCode(generateSeatCode())
                    .tableManagement(this)
                    .isActive(false)
                    .qrUrl("defaultQrUrl") // 필요에 따라 기본 URL 값 지정
                    .x(0.0) // 기본 x 좌표 값 지정
                    .y(0.0) // 기본 y 좌표 값 지정
                    .build();
            this.seats.add(seat); // 좌석을 TableManagement의 seats 리스트에 추가
            seatService.saveSeat(seat); // 데이터베이스에 좌석 저장
        }
    }

    public String generateSeatCode(){
        Random r=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0; i<6; i++){
            sb.append(r.nextInt(10));
        }

        return sb.toString();
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats=numberOfSeats;
    }

}
