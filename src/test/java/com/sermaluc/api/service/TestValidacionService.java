package com.sermaluc.api.service;

import com.sermaluc.api.dto.SignUpRqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestValidacionService {

    @InjectMocks
    private ValidacionService validacionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        validacionService.setEmailRegex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        validacionService.setPasswordRegex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
    }

    @Test
    public void testValidaNombre() {
        SignUpRqDto userDto = new SignUpRqDto();
        userDto.setName("Juan");
        userDto.setEmail("juan@gmail.com");
        userDto.setPassword("a2asfGfdfdf4");

        validacionService.validateUser(userDto);
    }

    @Test
    public void testValidaNombreNull() {
        SignUpRqDto userDto = new SignUpRqDto();
        userDto.setEmail("juan@gmail.com");
        userDto.setPassword("a2asfGfdfdf4");

        assertThrows(IllegalArgumentException.class, () ->
            validacionService.validateUser(userDto), "El nombre es obligatorio");
    }

    @Test
    public void testValidaMailInvalido() {
        SignUpRqDto userDto = new SignUpRqDto();
        userDto.setName("Juan");
        userDto.setEmail("juan@gmail");

        assertThrows(IllegalArgumentException.class, () ->
            validacionService.validateUser(userDto), "El correo electrónico no es válido");
    }

    @Test
    public void testValidaClaveInvalida() {
        SignUpRqDto userDto = new SignUpRqDto();
        userDto.setName("Juan");
        userDto.setEmail("juan@gmail.com");
        userDto.setPassword("123");

        assertThrows(IllegalArgumentException.class, () ->
            validacionService.validateUser(userDto), "La contraseña no cumple con los requisitos");
    }

    @Test
    public void testValidaClaveSinMinuscula() {
        SignUpRqDto userDto = new SignUpRqDto();
        userDto.setName("Juan");
        userDto.setEmail("juan@gmail.com");
        userDto.setPassword("PASSWORD123");

        assertThrows(IllegalArgumentException.class, () ->
            validacionService.validateUser(userDto), "La contraseña debe contener al menos una letra minúscula");
    }

    @Test
    public void testValidaClaveSinMayuscula() {
        SignUpRqDto userDto = new SignUpRqDto();
        userDto.setName("Juan");
        userDto.setEmail("juan@gmail.com");
        userDto.setPassword("password123");

        assertThrows(IllegalArgumentException.class, () ->
            validacionService.validateUser(userDto), "La contraseña debe contener al menos una letra mayúscula");
    }

    @Test
    public void testValidaClaveSinNumero() {
        SignUpRqDto userDto = new SignUpRqDto();
        userDto.setName("Juan");
        userDto.setEmail("juan@gmail.com");
        userDto.setPassword("Password");

        assertThrows(IllegalArgumentException.class, () ->
            validacionService.validateUser(userDto), "La contraseña debe contener al menos un número");
    }

}
