package com.bankoperations.bankoperations.controller;

import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.exception.InvalidUserException;
import com.bankoperations.bankoperations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user) throws InvalidUserException {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (InvalidUserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user: " + e.getMessage());
        }
    }
}
