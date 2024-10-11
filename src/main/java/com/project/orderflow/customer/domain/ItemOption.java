package com.project.orderflow.customer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "item_option") // 테이블 이름을 'item_option'으로 변경
@NoArgsConstructor
public class ItemOption { // 엔티티 이름 변경

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "옵션 이름을 입력해주세요")
    private String optionName;

    @NotNull
    private Long ownerId; // 매장 ID로 구분

    @Builder
    public ItemOption(String optionName, Long ownerId) {
        this.optionName = optionName;
        this.ownerId = ownerId;
    }
}