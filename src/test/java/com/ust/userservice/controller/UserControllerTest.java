package com.ust.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.userservice.exception.UserAlreadyExistsException;
import com.ust.userservice.model.User;
import com.ust.userservice.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;
    private User user;

    private List<User> users;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User("Jack998", "Jack", "Phil", "1234", "Jack@123.com");

    }

    @Test
    void registerUserTest() throws Exception {
        Assertions.assertThrows(NullPointerException.class, () -> {
            when(userService.saveUser(user)).thenThrow(UserAlreadyExistsException.class);
            mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                    .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        });

    }

    @Test
    void loginUserTest() throws Exception {
        Assertions.assertThrows(NullPointerException.class, () -> {
            String userId = "Jack998";
            String password = "1234";
            Mockito.when(userService.saveUser(user)).thenReturn(true);
            Mockito.when(userService.findByUserIdAndPassword(userId, password)).thenReturn(user);
            mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                    .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        });
    }

    @Test
    void updateUserTest() throws Exception {
        Assertions.assertThrows(NullPointerException.class, () -> {
            user.setUserPassword("23456789");
            when(userService.updateUser(eq(user.getUserId()), any())).thenReturn(user);
            mockMvc.perform(put("/user/Jack998")
                    .contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                    .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        });
    }

    @Test
    void deleteUserTest() throws Exception {
        Assertions.assertThrows(NullPointerException.class, () -> {
            when(userService.deleteUser("Jack998")).thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.delete("/user/Jack998")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        });


    }

    @Test
    void getUserByIdUserTest() throws Exception {
        Assertions.assertThrows(NullPointerException.class, () -> {
            when(userService.getUserById("Jack998")).thenReturn(user);
            mockMvc.perform(get("/user/Jack998").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        });

    }

    @Test
    void getAllUsersUserTest() throws Exception {
        Assertions.assertThrows(NullPointerException.class, () -> {
            when(userService.getAllUsers()).thenReturn(users);
            mockMvc.perform(get("/user/AllUsers").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        });
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}