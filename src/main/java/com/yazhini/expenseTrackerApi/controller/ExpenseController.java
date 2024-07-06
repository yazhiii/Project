package com.yazhini.expenseTrackerApi.controller;
import com.yazhini.expenseTrackerApi.DTO.CategoryDTO;
import com.yazhini.expenseTrackerApi.DTO.ExpenseDTO;
import com.yazhini.expenseTrackerApi.entity.ExpenseEntity;
import com.yazhini.expenseTrackerApi.io.CategoryResponse;
import com.yazhini.expenseTrackerApi.io.ExpenseRequest;
import com.yazhini.expenseTrackerApi.io.ExpenseResponse;
import com.yazhini.expenseTrackerApi.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getAllExpenses(Pageable page)
    {
        List<ExpenseDTO> listOfExpenses=expenseService.getAllExpenses(page);
        return listOfExpenses.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());


    }

    @GetMapping("/expenses/{id}")
    public ExpenseEntity  getAllExpenses(@PathVariable Long id)
    {
        //System.out.println(100/0);-Internal Server Error Testing
        return expenseService.getExpenseById(id);

    }

    @ResponseStatus(value= HttpStatus.NO_CONTENT)
    @DeleteMapping ("/expenses")
    public void  deleteExpenses(@RequestParam Long id)
    {
       expenseService.deleteExpenseById(id);

    }

    @ResponseStatus(value= HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expense)
    {
        ExpenseDTO expenseDTO=mapToDTO(expense);
        expenseDTO=expenseService.saveExpenseDetails(expenseDTO);
        return mapToResponse(expenseDTO);
    }

    private ExpenseResponse mapToResponse(ExpenseDTO expenseDTO) {
       return  ExpenseResponse.builder()
                .name(expenseDTO.getName())
                .expenseId(expenseDTO.getExpenseId())
                .description(expenseDTO.getDescription())
                .amount(expenseDTO.getAmount())
                .date(expenseDTO.getDate())
                .createdAt(expenseDTO.getCreatedAt())
                .updatedAt(expenseDTO.getUpdatedAt())
                .category(mapToCategoryResponse(expenseDTO.getCategoryDTO()))
                .build();

    }

    private CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .build();

    }

    private ExpenseDTO mapToDTO(ExpenseRequest expense) {
        return ExpenseDTO.builder()
                .name(expense.getName())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .categoryID(expense.getCategoryId())
                .build();
    }

    @PutMapping("/expenses/{id}")
    public ExpenseEntity updateExpenseDetails(@RequestBody ExpenseEntity expense,@PathVariable Long id)
    {
        return expenseService.updateExpenseDetails(expense,id);

    }

    @GetMapping("/expenses/category")
    public List<ExpenseEntity> getAllExpensesByCategory(@RequestParam String category,Pageable page)
    {
        return expenseService.readByCategory(category, page);
    }

    @GetMapping("/expenses/name")
    public List<ExpenseEntity> getAllExpenseByName(@RequestParam String name, Pageable page)
    {
        return expenseService.readByName(name,page);
    }

    @GetMapping("/expenses/date")
    public List<ExpenseEntity>  getAllExpensesByDate(@RequestParam (required = false)Date startDate, @RequestParam (required = false) Date endDate, Pageable page)
    {
        return expenseService.readByDate(startDate,endDate,page);

    }
}
