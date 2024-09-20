package com.project.orderflow.customer.repository;

import com.project.orderflow.customer.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
