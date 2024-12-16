package com.sermaluc.api.controller;

import com.sermaluc.api.dto.ErrorRsDto;
import com.sermaluc.api.dto.SignUpRqDto;
import com.sermaluc.api.model.UserModel;
import com.sermaluc.api.service.HandleErrorService;
import com.sermaluc.api.service.UserService;
import com.sermaluc.api.service.ValidacionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class TestUserController {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ValidacionService validacionService;

    @Mock
    private HandleErrorService handleErrorService;

    @Mock
    private HttpServletRequest request;

    private SignUpRqDto signUpRequestDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        signUpRequestDto = new SignUpRqDto();
        signUpRequestDto.setName("Juan");
        signUpRequestDto.setEmail("juan@gmail.com");
        signUpRequestDto.setPassword("a2asfGfdfdf4");
    }

    @Test
    public void testSignUpSuccess() {
        UserModel createdUser = new UserModel();
        createdUser.setName("Juan");
        createdUser.setEmail("juan@gmail.com");

        doNothing().when(validacionService).validateUser(signUpRequestDto);
        when(userService.signUp(signUpRequestDto)).thenReturn(createdUser);

        ResponseEntity<?> response = userController.signUp(signUpRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdUser, response.getBody());
    }

    @Test
    public void testSignUpFailureInvalidData() {

        UserModel createdUser = new UserModel();
        createdUser.setEmail("juan@gmail.com");

        doNothing().when(validacionService).validateUser(signUpRequestDto);

        doThrow(new IllegalArgumentException("El nombre es obligatorio")).when(validacionService).validateUser(signUpRequestDto);

        ErrorRsDto errorRsDto = new ErrorRsDto("El nombre es obligatorio");
        when(handleErrorService.handleError(any(IllegalArgumentException.class))).thenReturn(errorRsDto);

        ResponseEntity<?> response = userController.signUp(signUpRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorRsDto errorResponse = (ErrorRsDto) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("El nombre es obligatorio", errorResponse.getMensaje());
    }

    @Test
    public void testGetAllUsersSuccess() {
        List<UserModel> users = new ArrayList<>();
        UserModel user1 = new UserModel();
        user1.setName("Juan");
        user1.setEmail("juan@gmail.com");
        UserModel user2 = new UserModel();
        user2.setName("Maria");
        user2.setEmail("maria@gmail.com");
        users.add(user1);
        users.add(user2);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<?> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetAllUsersNoContent() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = userController.getAllUsers();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ErrorRsDto errorResponse = (ErrorRsDto) response.getBody();
        assertEquals("No se encontraron usuarios", errorResponse.getMensaje());
    }

    @Test
    public void testGetAllUsersFailure() {
        when(userService.getAllUsers()).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = userController.getAllUsers();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


}
