package com.sermaluc.api.repository;

import com.sermaluc.api.model.PhoneModel;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<PhoneModel,UUID> {
    PhoneModel findAllByid(UUID id);
}
