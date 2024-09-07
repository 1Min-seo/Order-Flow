package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.Feedback;
import com.project.orderflow.customer.dto.FeedbackDto;
import com.project.orderflow.customer.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public void saveFeedback(FeedbackDto feedbackDto) {

        Feedback feedback = Feedback.builder()
                .tableNumber(feedbackDto.getTableNumber())
                .score(feedbackDto.getScore())
                .comment(feedbackDto.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        feedbackRepository.save(feedback);
    }

    // 모든 피드백 조회
    public List<FeedbackDto> getAllFeedback() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return feedbackList.stream()
                .map(feedback -> new FeedbackDto(
                        feedback.getTableNumber(),
                        feedback.getScore(),
                        feedback.getComment(),
                        feedback.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}