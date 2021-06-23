package com.ust.userservice.model;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user = new User("Jack998", "Jack", "Phil", "1234", "Jack@123.com");

    @Test
    public void UserTest() {
        new BeanTester().testBean(User.class);
    }



}