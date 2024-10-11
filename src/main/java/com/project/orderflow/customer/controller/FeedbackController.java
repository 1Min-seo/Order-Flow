package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.Feedback;
import com.project.orderflow.customer.dto.FeedbackDto;
import com.project.orderflow.customer.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "피드백 신청(고객)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값을 넣어주시고, tableNumber에는 해당 테이블의 번호를 써주세요. socre는 숫자로 넣어주세요. reqiestedAt 시간은 yyyy.MM.dd HH:mm 이런 형식으로 넣어주세용"
    )
    @PostMapping("/submit/{ownerId}")
    public ResponseEntity<String> submitFeedback(@PathVariable Long ownerId, @RequestBody FeedbackDto feedbackDto) {
        feedbackService.submitFeedback(ownerId, feedbackDto);
        return ResponseEntity.ok("피드백이 제출되었습니다.");
    }

    @Operation(
            summary = "피드백 리스트 조회(관리자)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값을 넣어주세요"
    )
    @GetMapping("/list/{ownerId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByOwnerId(@PathVariable Long ownerId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByOwnerId(ownerId);
        return ResponseEntity.ok(feedbacks);
    }

    @Operation(
            summary = "피드백 댓글(관리자)",
            description = "feedbackId에는 해당 피드백의 id값 comment는 댓글"
    )
    @PostMapping("/comment/{feedbackId}")
    public ResponseEntity<String> addCommentToFeedback(@PathVariable Long feedbackId, @RequestParam String comment) {
        feedbackService.addCommentToFeedback(feedbackId, comment);
        return ResponseEntity.ok("댓글이 추가되었습니다.");
    }
}