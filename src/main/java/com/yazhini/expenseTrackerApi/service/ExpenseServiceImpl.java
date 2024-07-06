package com.yazhini.expenseTrackerApi.service;

import com.yazhini.expenseTrackerApi.DTO.CategoryDTO;
import com.yazhini.expenseTrackerApi.DTO.ExpenseDTO;
import com.yazhini.expenseTrackerApi.Exception.ResourceNotFoundException;
import com.yazhini.expenseTrackerApi.entity.CategoryEntity;
import com.yazhini.expenseTrackerApi.entity.ExpenseEntity;
import com.yazhini.expenseTrackerApi.repository.CategoryRepository;
import com.yazhini.expenseTrackerApi.repository.ExpenseRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{

   private final UserService userService;


    private final  ExpenseRepository expenseRepo;

    private final CategoryRepository categoryRepo;



    @Override
    public List<ExpenseDTO> getAllExpenses(Pageable page) {
        List<ExpenseEntity> listOfExpenses=expenseRepo.findByUserId(userService.getLoggedInUser().getId(),page).toList();
        return listOfExpenses.stream().map(expenseEntity -> mapToDTO(expenseEntity)).collect(Collectors.toList());
    }

    @Override
    public ExpenseEntity getExpenseById(Long id) throws ResourceNotFoundException {
        Optional<ExpenseEntity> expense=expenseRepo.findById(id);
        if(expense.isPresent())
            return expense.get();
        throw new ResourceNotFoundException("Expense Id not Found:"+id);

    }

    @Override
    public void deleteExpenseById(Long id) {
        ExpenseEntity expense=getExpenseById(id);
        expenseRepo.delete(expense);
    }

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        Optional<CategoryEntity> optionalCategory=categoryRepo.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), expenseDTO.getCategoryID());
        if(!optionalCategory.isPresent())
            throw new ResourceNotFoundException("Category NOt Found:"+expenseDTO.getCategoryID());
        expenseDTO.setExpenseId(UUID.randomUUID().toString());
        ExpenseEntity newExpense=mapToEntity(expenseDTO);
        newExpense.setCategory(optionalCategory.get());
        newExpense.setUser(userService.getLoggedInUser());
       newExpense= expenseRepo.save(newExpense);
       return mapToDTO(newExpense);


    }

    private ExpenseDTO mapToDTO(ExpenseEntity newExpense) {
        return ExpenseDTO.builder()
                .expenseId(newExpense.getExpenseId())
                .name(newExpense.getName())
                .description(newExpense.getDescription())
                .amount(newExpense.getAmount())
                .date(newExpense.getDate())
                .createdAt(newExpense.getCreatedAt())
                .updatedAt(newExpense.getUpdatedAt())
                .categoryDTO(mapToCategoryDTO(newExpense.getCategory()))
                .build();
    }

    private CategoryDTO mapToCategoryDTO(CategoryEntity category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .categoryId(category.getCategoryId())
                .build();
    }

    private ExpenseEntity mapToEntity(ExpenseDTO expenseDTO) {
        return ExpenseEntity.builder()
                .expenseId(expenseDTO.getExpenseId())
                .name(expenseDTO.getName())
                .description(expenseDTO.getDescription())
                .date(expenseDTO.getDate())
                .amount(expenseDTO.getAmount())
                .build();
    }

    @Override
    public ExpenseEntity updateExpenseDetails(ExpenseEntity expense, Long id) {
        ExpenseEntity existingExpense=getExpenseById(id);
        existingExpense.setName(expense.getName()!=null? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription()!=null? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setCategory(expense.getCategory()!=null? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setAmount(expense.getAmount()!=null? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setDate(expense.getDate()!=null? expense.getDate() : existingExpense.getDate());
        return expenseRepo.save(existingExpense);

    }

    @Override
    public List<ExpenseEntity> readByCategory(String category, Pageable page) {
        return expenseRepo.findByCategory(category,page).toList();
    }

    @Override
    public List<ExpenseEntity> readByName(String name, Pageable page) {
        return expenseRepo.findByNameContaining(name,page).toList();
    }

    @Override
    public List<ExpenseEntity> readByDate(Date startDate, Date endDate, Pageable page) {
        if(startDate==null)
            startDate= new Date(0);
        if(endDate==null)
            endDate=new Date(System.currentTimeMillis());

        return expenseRepo.findByDateBetween(startDate,endDate, page).toList();
    }
}
