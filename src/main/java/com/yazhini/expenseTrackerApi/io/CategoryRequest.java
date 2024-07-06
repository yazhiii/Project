package com.yazhini.expenseTrackerApi.io;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private String name;
    private String description;
    private String categoryIcon;

}
