package com.ust.userservice.service;

import com.ust.userservice.exception.UserAlreadyExistsException;
import com.ust.userservice.exception.UserNotFoundException;
import com.ust.userservice.model.User;
import com.ust.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository repo;

    @Override
    public User findByUserIdAndPassword(String userId, String userPassword) throws UserNotFoundException {
        User user=repo.findByUserIdAndUserPassword(userId, userPassword);
        if(user ==null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public boolean saveUser(User user) throws UserAlreadyExistsException {
        if(repo.findById(user.getUserId()).isPresent()){
            throw new UserAlreadyExistsException("User already exist");
        }
        repo.save(user);
        return true;
    }

    @Override
    public User updateUser(String userId, User user) throws UserNotFoundException {
        try {
            repo.save(user);
            return repo.findById(userId).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public boolean deleteUser(String userId) throws UserNotFoundException {
        try {
            User fecthedUser = repo.findById(userId).get();
            if (fecthedUser != null) {
                repo.delete(fecthedUser);
            }
        } catch (NoSuchElementException exception) {
            throw new UserNotFoundException("User does not exists");
        }
        return true;
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        try {
            return repo.findById(userId).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
