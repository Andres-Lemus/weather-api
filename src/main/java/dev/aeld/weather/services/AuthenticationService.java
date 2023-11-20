package dev.aeld.weather.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.aeld.weather.dtos.LoginUserDto;
import dev.aeld.weather.dtos.RegisterUserDto;
import dev.aeld.weather.entities.User;
import dev.aeld.weather.repositories.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto registerUser) {
        User user = new User();
        user.setName(registerUser.getName());
        user.setLastName(registerUser.getLastName());
        user.setEmail(registerUser.getEmail());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));

        return userRepository.save(user);
    }

    public User signin(LoginUserDto loginUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()));

        return userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow();
    }
}
