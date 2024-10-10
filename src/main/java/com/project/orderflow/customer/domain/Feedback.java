package com.project.orderflow.customer.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    private String tableNumber;

    private double score;

    @Column(columnDefinition="TEXT")
    private String comment;

    private LocalDateTime createdAt;

    private String reply;

    @Builder
    public Feedback(Long ownerId, String tableNumber, double score, String comment, LocalDateTime createdAt) {
        this.ownerId = ownerId;
        this.tableNumber = tableNumber;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public void addComment(String additionalComment) {
        this.comment = this.comment + "\n" + additionalComment;
    }

    public void addReply(String additionalReply) {
        this.reply = additionalReply;
    }
}