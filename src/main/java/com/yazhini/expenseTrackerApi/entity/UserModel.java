package com.yazhini.expenseTrackerApi.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {
    @NotBlank(message="name should not b empty")
    private String name;

    @Email
    @NotNull(message="Email should not b null")
    private String email;

    @NotNull(message="Password should not b null")
    @Size(min=5,message="Atleast 5 chars in password")
    private String password;

    //default value
    private Long age = 0L;
}

