package com.yazhini.expenseTrackerApi.controller;


import com.yazhini.expenseTrackerApi.DTO.CategoryDTO;
import com.yazhini.expenseTrackerApi.io.CategoryRequest;
import com.yazhini.expenseTrackerApi.io.CategoryResponse;
import com.yazhini.expenseTrackerApi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public  void deleteByCategory(@PathVariable String categoryId)
    {
        categoryService.deleteCategory(categoryId);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest)
    {
        CategoryDTO categoryDTO=mapToDTO(categoryRequest);
        categoryDTO=categoryService.saveCategory(categoryDTO);
        return mapToResponse(categoryDTO);
    }

    @GetMapping
    public List<CategoryResponse> readCategories()
    {
        List<CategoryDTO> listOfCatgories=categoryService.getAllCategories();
        return listOfCatgories.stream().map(categoryDTO -> mapToResponse(categoryDTO)).collect(Collectors.toList());
    }

    private CategoryResponse mapToResponse(CategoryDTO categoryDTO) {
       return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .createdAt(categoryDTO.getCreatedAt())
                .updatedAt(categoryDTO.getUpdatedAt())
                .build();
    }

    private CategoryDTO mapToDTO(CategoryRequest categoryRequest) {
       return  CategoryDTO.builder().name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .categoryIcon(categoryRequest.getCategoryIcon())
                .build();

    }
}
