package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.Feedback;
import com.project.orderflow.customer.dto.FeedbackDto;
import com.project.orderflow.customer.service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit/{ownerId}")
    public ResponseEntity<String> submitFeedback(@PathVariable Long ownerId, @RequestBody FeedbackDto feedbackDto) {
        feedbackService.submitFeedback(ownerId, feedbackDto);
        return ResponseEntity.ok("피드백이 제출되었습니다.");
    }

    @GetMapping("/list/{ownerId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByOwnerId(@PathVariable Long ownerId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByOwnerId(ownerId);
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/comment/{feedbackId}")
    public ResponseEntity<String> addCommentToFeedback(@PathVariable Long feedbackId, @RequestParam String comment) {
        feedbackService.addCommentToFeedback(feedbackId, comment);
        return ResponseEntity.ok("댓글이 추가되었습니다.");
    }
}