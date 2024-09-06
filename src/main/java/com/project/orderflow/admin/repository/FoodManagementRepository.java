package com.project.orderflow.admin.repository;

import com.project.orderflow.admin.domain.FoodManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodManagementRepository extends JpaRepository<FoodManagement, Long> {

}
