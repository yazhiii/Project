package com.yazhini.expenseTrackerApi.io;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {
    @NotNull(message="Expense name must not be null")
    @Size(min=3,message="Expense name must be 3 chars")
    private String name;

    private String description;

    private BigDecimal amount;
    private String categoryId;
    private Date date;
}
