package dev.aeld.weather.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import dev.aeld.weather.entities.User;
import dev.aeld.weather.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Cacheable(cacheNames = "UserCache", unless="#result == null")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().
        getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Cacheable(cacheNames = "UserCache", unless="#result == null")
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

}
