package com.ust.userservice.service;

import com.ust.userservice.exception.UserAlreadyExistsException;
import com.ust.userservice.exception.UserNotFoundException;
import com.ust.userservice.model.User;
import com.ust.userservice.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @Mock
    UserRepository repo;

    @InjectMocks
    UserServiceImpl userService;

    List<User> userList = new ArrayList<>();

    Optional<User> options;

    User user = new User("Jack998", "Jack", "Phil", "1234", "Jack@123.com");


    @Test
    public void findByUserIdAndPasswordTest() throws UserNotFoundException {
        Mockito.when(repo.findByUserIdAndUserPassword("Jack998", "1234")).thenReturn(user);
        User fetchedUser = userService.findByUserIdAndPassword("Jack998", "1234");
        Assert.assertEquals("Jack998", fetchedUser.getUserId());
    }

    @Test
    public void saveUserTest() throws UserAlreadyExistsException {
        Mockito.when(repo.save(user)).thenReturn(user);
        boolean flag = userService.saveUser(user);
        Assert.assertEquals("Cannot Register User", true, flag);

    }

    @Test
    public void updateUserTest() throws UserNotFoundException {
        Assertions.assertThrows(NullPointerException.class, () -> {
        Mockito.when(repo.findById(user.getUserId())).thenReturn(options);
        user.setUserPassword("1234567789");
        User fetchuser = userService.updateUser(user.getUserId(), user);
        assertEquals(user, fetchuser);
    });

    }

    @Test
    public void deleteUserTest() throws UserNotFoundException {
        Assertions.assertThrows(NullPointerException.class, () -> {
        Mockito.when(repo.findById(user.getUserId())).thenReturn(options);
        boolean flag = userService.deleteUser(user.getUserId());
        assertEquals(true, flag);
        });

    }

    @Test
    public void getUserByIdTest() throws UserNotFoundException {
        Assertions.assertThrows(NullPointerException.class, () -> {
        Mockito.when(repo.findById(user.getUserId())).thenReturn(options);
        User fetchedUser = userService.getUserById(user.getUserId());
        assertEquals(user, fetchedUser);
        });

    }
    @Test
    public void getAllUsersTest()
    {
        Mockito.when(repo.findAll()).thenReturn(userList);
        List<User> users = userService.getAllUsers();
        Assert.assertEquals(userList, userList);

    }

}