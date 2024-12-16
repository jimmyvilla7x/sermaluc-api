package com.sermaluc.api.service;

import com.sermaluc.api.dto.PhoneDto;
import com.sermaluc.api.model.PhoneModel;
import com.sermaluc.api.model.UserModel;
import com.sermaluc.api.repository.PhoneRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void saveUserPhones(UserModel user, List<PhoneDto> phoneDtos) {
        List<PhoneModel> phoneModels = phoneDtos.stream()
            .map(PhoneDto::createEntity)
            .collect(Collectors.toList());

        phoneModels.forEach(phone -> {
            phone.setUser(user);
            phoneRepository.save(phone);
        });

        user.setPhones(phoneModels);
    }
}
