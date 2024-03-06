package com.bankoperations.bankoperations.controller;

import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.exception.InvalidUserException;
import com.bankoperations.bankoperations.exception.NoContactInfoException;
import com.bankoperations.bankoperations.exception.UserNotFoundException;
import com.bankoperations.bankoperations.model.UpdateEmailRequest;
import com.bankoperations.bankoperations.model.UpdatePhoneRequest;
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
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (InvalidUserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user: " + e.getMessage());
        }
    }

    @PutMapping("/updateEmail/{userID}")
    public ResponseEntity<?> updateEmail(@PathVariable Long userID,
                                         @RequestBody UpdateEmailRequest updateEmailRequest) {
        try {
            return ResponseEntity.ok(userService.updateEmail(userID, updateEmailRequest));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updatePhone/{userID}")
    public ResponseEntity<?> updatePhone(@PathVariable Long userID,
                                         @RequestBody UpdatePhoneRequest updatePhoneRequest) {
        try {
            return ResponseEntity.ok(userService.updatePhoneNumber(userID, updatePhoneRequest));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/deleteEmail/{userId}")
    public ResponseEntity<?> deleteEmail(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userService.deleteEmail(userId));
        } catch (UserNotFoundException | NoContactInfoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/deletePhone/{userId}")
    public ResponseEntity<?> deletePhoneNumber(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userService.deletePhoneNumber(userId));
        } catch (UserNotFoundException | NoContactInfoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
