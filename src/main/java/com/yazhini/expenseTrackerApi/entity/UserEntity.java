package com.yazhini.expenseTrackerApi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name="tbl_users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique=true)
    private String email;

   //To never return password
    @JsonIgnore
    private  String password;

    private Long age;

    @Column(name="created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp created_at;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Timestamp updated_at;

}
