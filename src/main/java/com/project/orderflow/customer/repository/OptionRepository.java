package com.project.orderflow.customer.repository;

import com.project.orderflow.customer.domain.ItemOption; // Option을 ItemOption으로 변경
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<ItemOption, Long> { // Option을 ItemOption으로 변경
    List<ItemOption> findByOwnerId(Long ownerId); // 반환 타입도 ItemOption으로 변경
}