package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.dto.FeedbackDto;
import com.project.orderflow.customer.service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Feedback API")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * 피드백 제출
      */
    @PostMapping
    public ResponseEntity<Void> createFeedback(@RequestBody FeedbackDto feedbackDTO) {
        feedbackService.saveFeedback(feedbackDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();  // 201 반환
    }

    /**
     * 모든 피드백 조회
      */
    @GetMapping("/list")
    public ResponseEntity<List<FeedbackDto>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }
}