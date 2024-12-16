package com.sermaluc.api.controller;

import com.sermaluc.api.dto.ErrorRsDto;
import com.sermaluc.api.dto.SignUpRqDto;
import com.sermaluc.api.model.UserModel;
import com.sermaluc.api.service.HandleErrorService;
import com.sermaluc.api.service.UserService;
import com.sermaluc.api.service.ValidacionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "User API")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidacionService validacionService;

    @Autowired
    private HandleErrorService handleErrorService;


    @ApiOperation("Endpoint para crear un usuario")
    @PostMapping(value = "/sign-up", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> signUp(@RequestBody SignUpRqDto signUpRequestDto) {
        try {
    
            validacionService.validateUser(signUpRequestDto);
    
            UserModel createdUser = userService.signUp(signUpRequestDto);
    
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            ErrorRsDto errorResponse = handleErrorService.handleError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ApiOperation("Endpoint para consultar todos los usuarios")
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserModel> users = userService.getAllUsers();

            if (users != null && !users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                ErrorRsDto errorResponse = new ErrorRsDto("No se encontraron usuarios");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorRsDto errorResponse = handleErrorService.handleError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
