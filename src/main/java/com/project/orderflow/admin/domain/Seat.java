package com.project.orderflow.admin.domain;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tableNumber;

    @Column(nullable = false, unique = true)
    private String authCode;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name="table_management_id", nullable = false)
    private TableManagement tableManagement;

    @Builder
    public Seat(String tableNumber, String authCode, TableManagement tableManagement, Boolean isActive){
        this.tableNumber=tableNumber;
        this.authCode=authCode;
        this.tableManagement=tableManagement;
        this.isActive=isActive;
    }

    public Boolean activateSeat(String authCode){
        if(this.authCode.equals(authCode)){
            this.isActive=true;
            return true;
        }
        return false;
    }

    public void setTableNumber(String newTableNumber){
        this.tableNumber=newTableNumber;
    }

    public void setTableManagement(TableManagement tableManagement){
        this.tableManagement=tableManagement;
    }
}
