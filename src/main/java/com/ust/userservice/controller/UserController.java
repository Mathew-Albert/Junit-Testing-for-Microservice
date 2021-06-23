package com.ust.userservice.controller;


import com.ust.userservice.exception.UserNotFoundException;
import com.ust.userservice.model.User;
import com.ust.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/register")
    @ApiOperation("Register a User")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            if(!service.saveUser(user)) {
                throw new Exception();
            }
            return new ResponseEntity<String>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/login")
    @ApiOperation("User Login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            service.findByUserIdAndPassword(user.getUserId(), user.getUserPassword());
            return new ResponseEntity<String>("Logged In Successfully!!!", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/user/{id}")
    @ApiOperation("Update User By ID")
    public ResponseEntity<?> update(@PathVariable() String id, @RequestBody User user) {
        try {
            return new ResponseEntity<User>(service.updateUser(id, user), HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/user/{id}")
    @ApiOperation("Delete User By ID")
    public ResponseEntity<String> delete(@PathVariable() String id) {
        try {
            service.deleteUser(id);
            return new ResponseEntity<String>("Successfully Deleted User with id: " + id, HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}")
    @ApiOperation("Get User By ID")
    public ResponseEntity<?> getUserById(@PathVariable() String id) {
        try {
            return new ResponseEntity<User>(service.getUserById(id), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/AllUsers")
    @ApiOperation("Get All the Users Registered")
    public ResponseEntity<List<User>>getAllUsers() {
        List<User>  users=service.getAllUsers();
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);

    }

}
