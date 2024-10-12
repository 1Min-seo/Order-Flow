package com.project.orderflow.admin.repository;

import com.project.orderflow.admin.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByTableManagementId(Long tableManagementId);

    Optional<Food> findByName(String name); // 음식 이름으로 음식 조회


}
