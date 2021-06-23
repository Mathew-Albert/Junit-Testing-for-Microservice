package com.ust.userservice.service;

import com.ust.userservice.exception.UserAlreadyExistsException;
import com.ust.userservice.exception.UserNotFoundException;
import com.ust.userservice.model.User;

import java.util.List;

public interface UserService {

    public User findByUserIdAndPassword(String userId, String userPassword) throws UserNotFoundException;

    boolean saveUser(User user) throws UserAlreadyExistsException;

    User updateUser(String userId, User user) throws UserNotFoundException;

    boolean deleteUser(String userId) throws UserNotFoundException;

    User getUserById(String userId) throws UserNotFoundException;

    public List<User> getAllUsers();


}
