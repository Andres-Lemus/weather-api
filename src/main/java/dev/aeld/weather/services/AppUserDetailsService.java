package dev.aeld.weather.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.aeld.weather.entities.User;
import dev.aeld.weather.repositories.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return optionalUser.get();
    }
}
