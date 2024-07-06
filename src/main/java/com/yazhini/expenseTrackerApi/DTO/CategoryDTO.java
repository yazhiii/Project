package com.yazhini.expenseTrackerApi.DTO;

import com.yazhini.expenseTrackerApi.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private String name;
    private String description;
    private String categoryIcon;
    private String categoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UserDTO user;
}
