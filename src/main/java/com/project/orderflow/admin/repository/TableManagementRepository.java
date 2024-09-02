package com.project.orderflow.admin.repository;
import com.project.orderflow.admin.domain.TableManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableManagementRepository extends JpaRepository<TableManagement, Long> {
}
