package com.yazhini.expenseTrackerApi.Exception;

public class ItemAlreadyExistsException extends RuntimeException{
    public ItemAlreadyExistsException(String message)
    {
        super(message);
    }
}
