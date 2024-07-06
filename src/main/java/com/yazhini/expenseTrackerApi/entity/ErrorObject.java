package com.yazhini.expenseTrackerApi.entity;

import lombok.Data;

@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private long timeStamp;
}
