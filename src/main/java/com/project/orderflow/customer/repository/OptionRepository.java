package com.project.orderflow.customer.repository;

import com.project.orderflow.customer.domain.OptionMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionMenu, Long> {
}