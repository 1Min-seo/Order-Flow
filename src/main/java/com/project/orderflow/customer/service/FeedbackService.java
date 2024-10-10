package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.Feedback;
import com.project.orderflow.customer.dto.FeedbackDto;
import com.project.orderflow.customer.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public void submitFeedback(Long ownerId, FeedbackDto feedbackDto) {
        Feedback feedback = Feedback.builder()
                .ownerId(ownerId)
                .tableNumber(feedbackDto.getTableNumber())
                .score(feedbackDto.getScore())
                .comment(feedbackDto.getComment())
                .createdAt(feedbackDto.getCreatedAt())
                .build();
        feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbacksByOwnerId(Long ownerId) {
        return feedbackRepository.findByOwnerId(ownerId);
    }

    public void addCommentToFeedback(Long feedbackId, String comment) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackId);
        if (feedbackOptional.isEmpty()) {
            throw new IllegalStateException("해당 피드백을 찾을 수 없습니다.");
        }
        Feedback feedback = feedbackOptional.get();
        feedback.addReply(comment);
        feedbackRepository.save(feedback);
    }
}