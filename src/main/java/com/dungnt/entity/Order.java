package com.dungnt.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Order extends PanacheEntity {
    private String orderId;
    private String type;
    private Integer status;
}
