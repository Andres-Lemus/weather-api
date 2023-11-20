package dev.aeld.weather.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aeld.weather.entities.User;
import dev.aeld.weather.services.UserService;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/weather/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Return data from the User logged")
    public ResponseEntity<User> authenticatedUser() {
        User currentUser = userService.authenticatedUser();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "List of Users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

}
