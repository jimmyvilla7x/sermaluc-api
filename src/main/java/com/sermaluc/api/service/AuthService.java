
package com.sermaluc.api.service;

import com.sermaluc.api.model.UserModel;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String generateJwtToken(UserModel user) {
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
            .compact();
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
