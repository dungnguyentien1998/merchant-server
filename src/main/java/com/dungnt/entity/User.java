package com.dungnt.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "app_user")
@Data
@Builder
@AllArgsConstructor
public class User extends PanacheEntity {
    @Column
    private String userId;

    @Column
    private String token;

    public User() {
    }
}
