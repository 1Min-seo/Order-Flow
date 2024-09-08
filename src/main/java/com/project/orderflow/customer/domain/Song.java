package com.project.orderflow.customer.domain;

import com.project.orderflow.customer.domain.enums.SongStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tableNumber;

    private String title;

    private String artist;

    @Enumerated(EnumType.STRING)
    private SongStatus status;  // Enum으로 상태 관리

    private LocalDateTime requestedAt;

    @Builder
    public Song(String tableNumber, String title, String artist, SongStatus status, LocalDateTime requestedAt) {
        this.tableNumber = tableNumber;
        this.title = title;
        this.artist = artist;
        this.status = status;
        this.requestedAt = requestedAt;
    }
}