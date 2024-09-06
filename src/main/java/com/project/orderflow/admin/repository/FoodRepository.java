package com.project.orderflow.admin.repository;

import com.project.orderflow.admin.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
