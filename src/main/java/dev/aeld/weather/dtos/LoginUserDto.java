package dev.aeld.weather.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    private String email;

    private String password;
}
