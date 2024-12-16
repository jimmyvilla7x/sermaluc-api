package com.sermaluc.api.service;

import com.sermaluc.api.dto.PhoneDto;
import com.sermaluc.api.dto.SignUpRqDto;
import com.sermaluc.api.exception.GeneralLogicException;
import com.sermaluc.api.model.PhoneModel;
import com.sermaluc.api.model.UserModel;
import com.sermaluc.api.repository.PhoneRepository;
import com.sermaluc.api.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    public UserModel signUp(SignUpRqDto request) {
    
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new GeneralLogicException("El usuario ya existe");
        }

        UserModel user= new UserModel();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        user.setToken(this.generateJwtToken(user));

        userRepository.save(user);

        if (request.getPhones().size() > 0) {
            List<PhoneModel> phoneModels = request.getPhones().stream()
                    .map(PhoneDto::createEntity)
                    .collect(Collectors.toList());
        
            phoneModels.forEach(phone -> {
                phone.setUser(user);
                phoneRepository.save(phone);
            });
        
            user.setPhones(phoneModels);
        }

        return user;
    }

    public String generateJwtToken(UserModel user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 10 dias
                .compact();
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

}

