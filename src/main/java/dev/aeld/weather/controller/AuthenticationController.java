package dev.aeld.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aeld.weather.dtos.LoginResponseDto;
import dev.aeld.weather.dtos.LoginUserDto;
import dev.aeld.weather.dtos.RegisterUserDto;
import dev.aeld.weather.entities.User;
import dev.aeld.weather.services.AuthenticationService;
import dev.aeld.weather.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/weather/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    @Operation(summary = "Register new User")
    public ResponseEntity<User> register(@Parameter(description = "The user's data to register") 
    @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/signin")
    @Operation(summary = "Login with a User in the application")
    public ResponseEntity<LoginResponseDto> authenticate(@Parameter(description = "E-mail and password of User to login") 
    @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.signin(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
