package com.project.orderflow.admin.repository;

import com.project.orderflow.admin.domain.orderlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderlistRepository extends JpaRepository<orderlist, Integer> {
    List<orderlist> findByTableorderid(Integer tableorderid);
}