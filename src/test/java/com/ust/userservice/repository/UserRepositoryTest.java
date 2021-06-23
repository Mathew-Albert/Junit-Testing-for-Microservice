package com.ust.userservice.repository;

import com.ust.userservice.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    User user = new User("Jack998", "Jack", "Phil", "1234", "Jack@123.com");

    @Test
    public void registerUserTest() {
        userRepository.save(user);
        User fetchedUser = userRepository.findById("Jack998").get();
        Assert.assertEquals(user.getUserId(), fetchedUser.getUserId());

    }

    @Test
    public void deleteUserTest() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
        userRepository.save(user);
        User fetcheduser = userRepository.findById("Jack998").get();
        Assert.assertEquals("Jack998", fetcheduser.getUserId());
        userRepository.delete(fetcheduser);
        fetcheduser = userRepository.findById("Jack998").get();
        });

    }

    @Test
    public void updateUserTest() {
        userRepository.save(user);
        User fetcheduser = userRepository.findById("Jack998").get();
        fetcheduser.setUserPassword("987654321");
        userRepository.save(fetcheduser);
        fetcheduser = userRepository.findById("Jack998").get();
        Assert.assertEquals("987654321", fetcheduser.getUserPassword());
    }

    @Test
    public void getUserByIdTest() {
        userRepository.save(user);
        User fetcheduser = userRepository.findById("Jack998").get();
        Assert.assertEquals(user.getUserId(),fetcheduser.getUserId());

    }

    @Test
    public void loginUserTest() {
        userRepository.save(user);
        User object = userRepository.findById(user.getUserId()).get();
        Assert.assertEquals(user.getUserId(), object.getUserId());
    }

    @Test
    public void getAllUserTest() {
        userRepository.save(user);
        List<User> users=userRepository.findAll();
        Assert.assertEquals(3,users.size());

    }

}