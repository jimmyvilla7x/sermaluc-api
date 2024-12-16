package com.sermaluc.api.service;

import com.sermaluc.api.dto.SignUpRqDto;
import com.sermaluc.api.exception.GeneralLogicException;
import com.sermaluc.api.model.UserModel;
import com.sermaluc.api.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PhoneService phoneService;
    private final AuthService authService;

    @Autowired
    public UserService(UserRepository userRepository, PhoneService phoneService, AuthService authService) {
        this.userRepository = userRepository;
        this.phoneService = phoneService;
        this.authService = authService;
    }

    public UserModel signUp(SignUpRqDto request) {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new GeneralLogicException("El usuario ya existe");
        }

        UserModel user = new UserModel();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(authService.hashPassword(request.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        user.setToken(authService.generateJwtToken(user));

        userRepository.save(user);

        if (!request.getPhones().isEmpty()) {
            phoneService.saveUserPhones(user, request.getPhones());
        }

        return user;
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
}


