package dev.aeld.weather.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.aeld.weather.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);
}
