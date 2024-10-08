package com.project.orderflow.customer.repository;

import com.project.orderflow.customer.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByOwnerId(Long ownerId);
}