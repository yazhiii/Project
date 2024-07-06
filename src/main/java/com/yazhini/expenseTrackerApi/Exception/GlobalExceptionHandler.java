package com.yazhini.expenseTrackerApi.Exception;

import com.yazhini.expenseTrackerApi.entity.ErrorObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest web)
    {
        ErrorObject obj= new ErrorObject();
        obj.setStatusCode(HttpStatus.NOT_FOUND.value());
        obj.setMessage(ex.getMessage());
        obj.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(obj,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleItemNotFoundException(ItemAlreadyExistsException ex, WebRequest web)
    {
        ErrorObject obj= new ErrorObject();
        obj.setStatusCode(HttpStatus.CONFLICT.value());
        obj.setMessage(ex.getMessage());
        obj.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(obj,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handleExpenseNotFoundException(MethodArgumentTypeMismatchException ex, WebRequest web)
    {
        ErrorObject obj= new ErrorObject();
        obj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        obj.setMessage(ex.getMessage());
        obj.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(obj,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleExpenseNotFoundException(Exception ex, WebRequest web)
    {
        ErrorObject obj= new ErrorObject();
        obj.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        obj.setMessage(ex.getMessage());
        obj.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(obj,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
    Map<String, Object> body = new HashMap<String, Object>();

        body.put("timestamp", new Date());

        body.put("statusCode", HttpStatus.BAD_REQUEST.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("messages", errors);

        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);

    }}




