package com.sermaluc.api.repository;

import com.sermaluc.api.model.UserModel;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findAllByid(UUID id);
    UserModel findByEmail(String email);
    UserModel findByToken(String token);
}
