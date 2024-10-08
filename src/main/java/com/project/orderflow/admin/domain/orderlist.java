package com.project.orderflow.admin.domain;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class orderlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String menu;

    @Column(nullable = false)
    private Integer quality;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer tableorderid;

}