package com.yazhini.expenseTrackerApi.service;

import com.yazhini.expenseTrackerApi.DTO.CategoryDTO;
import com.yazhini.expenseTrackerApi.DTO.UserDTO;
import com.yazhini.expenseTrackerApi.Exception.ItemAlreadyExistsException;
import com.yazhini.expenseTrackerApi.Exception.ResourceNotFoundException;
import com.yazhini.expenseTrackerApi.entity.CategoryEntity;
import com.yazhini.expenseTrackerApi.entity.UserEntity;
import com.yazhini.expenseTrackerApi.repository.CategoryRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatgoryServiceImpl implements  CategoryService{

    private final CategoryRepository categoryRepository;

    private final UserService userService;

    @Override
    public List<CategoryDTO> getAllCategories() {

        List<CategoryEntity> list=categoryRepository.findByUserId((userService.getLoggedInUser().getId()));
        return list.stream().map(categoryEntity->mapToDTO(categoryEntity)).collect(Collectors.toList());


    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        boolean  categoryEntity=categoryRepository.existsByNameAndUserId(categoryDTO.getName(), userService.getLoggedInUser().getId());
        if(categoryEntity)
            throw new ItemAlreadyExistsException("Category already exits");
        CategoryEntity newCategory= mapToEntity(categoryDTO);
        newCategory=categoryRepository.save(newCategory);
        return mapToDTO(newCategory);

    }

    @Override
    public void deleteCategory(String categoryId) {
        Optional<CategoryEntity> optionalCategory=categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), categoryId);
        if(!(optionalCategory.isPresent()))
            throw new ResourceNotFoundException("Category not present:"+categoryId);
        categoryRepository.delete(optionalCategory.get());


    }

    private CategoryEntity mapToEntity(CategoryDTO categoryDTO) {
        return CategoryEntity.builder().name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .categoryId(UUID.randomUUID().toString())
                .user(userService.getLoggedInUser())
                .build();
    }

    private CategoryDTO mapToDTO(CategoryEntity categoryEntity) {
        return CategoryDTO.builder()
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .categoryIcon(categoryEntity.getCategoryIcon())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .user(mapToUserDTO(categoryEntity.getUser()))
                .build();
    }

    private UserDTO mapToUserDTO(UserEntity user) {
       return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
