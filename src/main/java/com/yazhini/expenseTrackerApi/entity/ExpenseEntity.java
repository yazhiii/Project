package com.yazhini.expenseTrackerApi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="tbl_expenses")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String expenseId;

    @Column(name="expense_name")
    @NotNull(message="Expense name must not be null")
    @Size(min=3,message="Expense name must be 3 chars")
    private String name;

    private String description;

    @Column(name="expense_amount")
    private BigDecimal amount;


    private Date date;

    @Column(name="created_at",nullable=false,updatable=false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name="updated-at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="category_id",nullable = false)
    @OnDelete(action= OnDeleteAction.RESTRICT)
    private CategoryEntity category;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity user;
}
