package com.yazhini.expenseTrackerApi.controller;

import com.yazhini.expenseTrackerApi.Exception.ResourceNotFoundException;
import com.yazhini.expenseTrackerApi.entity.UserEntity;
import com.yazhini.expenseTrackerApi.entity.UserModel;
import com.yazhini.expenseTrackerApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<UserEntity> save(@Valid @RequestBody UserModel user)
//    {
//        return new ResponseEntity<UserEntity>(userService.createUser(user),HttpStatus.CREATED);
//    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> get(@PathVariable Long id)
    {
        return new ResponseEntity<UserEntity>(userService.read(id),HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserEntity> update (@RequestBody UserEntity user,@PathVariable Long id)
    {
        UserEntity mUser=userService.update(user, id);
        return new ResponseEntity<UserEntity>(mUser,HttpStatus.OK);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) throws ResourceNotFoundException
    {
        userService.delete(id);return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
