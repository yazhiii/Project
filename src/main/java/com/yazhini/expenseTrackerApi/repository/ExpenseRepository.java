package com.yazhini.expenseTrackerApi.repository;

import com.yazhini.expenseTrackerApi.entity.CategoryEntity;
import com.yazhini.expenseTrackerApi.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {
        Page<ExpenseEntity> findByCategory(String category, Pageable page);

        Page<ExpenseEntity> findByNameContaining (String keyword,Pageable page);

        Page<ExpenseEntity> findByDateBetween (Date startDate, Date endDate, Pageable page);

        Page<ExpenseEntity> findByUserId(Long userId,Pageable page);
}
