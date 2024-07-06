package com.yazhini.expenseTrackerApi.DTO;

import com.yazhini.expenseTrackerApi.io.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDTO {
    private String expenseId;
    private String name;
    private BigDecimal amount;
    private String description;
    private Date date;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private CategoryDTO categoryDTO;
    private UserDTO userDTO;
    private String categoryID;
}
