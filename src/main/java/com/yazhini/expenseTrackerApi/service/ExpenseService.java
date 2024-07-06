package com.yazhini.expenseTrackerApi.service;

import com.yazhini.expenseTrackerApi.DTO.ExpenseDTO;
import com.yazhini.expenseTrackerApi.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ExpenseService {

    public List<ExpenseDTO> getAllExpenses(Pageable page);

    public ExpenseEntity getExpenseById(Long id);

    public void deleteExpenseById(Long id);

    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);

    public ExpenseEntity updateExpenseDetails(ExpenseEntity expense, Long id);

    List<ExpenseEntity> readByCategory(String category,Pageable page);

    List<ExpenseEntity> readByName(String name,Pageable page);

    List<ExpenseEntity> readByDate(Date startDate, Date endDate, Pageable page);
}
