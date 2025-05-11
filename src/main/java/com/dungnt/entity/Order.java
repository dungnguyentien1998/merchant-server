package com.dungnt.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "app_order")
@Data
@Builder
@AllArgsConstructor
public class Order extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String orderId;
    private String type;
    private Integer status;
    private Integer amount;
    private Integer refundStatus;

    public Order() {
    }
}
