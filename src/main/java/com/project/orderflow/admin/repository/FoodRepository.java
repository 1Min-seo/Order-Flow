package com.project.orderflow.admin.repository;

import com.project.orderflow.admin.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByTableManagementId(Long tableManagementId);


}
