package com.sermaluc.api.service;

import com.sermaluc.api.dto.SignUpRqDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidacionService {

    private String emailRegex;
    private String passwordRegex;

    @Value("${validation.regex.email}")
    public void setEmailRegex(String emailRegex) {
        this.emailRegex = emailRegex;
    }

    @Value("${validation.regex.password}")
    public void setPasswordRegex(String passwordRegex) {
        this.passwordRegex = passwordRegex;
    }

    public void validateUser(SignUpRqDto userDto) {
        if (userDto.getName() == null || userDto.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (userDto.getEmail() == null || !userDto.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("El correo electrónico no es válido");
        }
        if (userDto.getPassword() == null || userDto.getPassword().length() < 10) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 10 caracteres");
        }
        if (!userDto.getPassword().matches(".*[a-z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra minúscula");
        }
        if (!userDto.getPassword().matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula");
        }
        if (!userDto.getPassword().matches(".*\\d.*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un número");
        }
    }
}
