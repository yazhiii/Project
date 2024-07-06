package com.yazhini.expenseTrackerApi.io;


import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String name;
    private String description;
    private String categoryIcon;
    private String categoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
