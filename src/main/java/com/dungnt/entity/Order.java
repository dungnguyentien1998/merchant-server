package com.dungnt.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_order")
@Data
@Builder
@AllArgsConstructor
public class Order extends PanacheEntity {
    private String orderId;
    private String type;
    private Integer status;

    public Order() {
    }
}
