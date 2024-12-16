package com.sermaluc.api.service;

import com.sermaluc.api.dto.PhoneDto;
import com.sermaluc.api.dto.SignUpRqDto;
import com.sermaluc.api.exception.GeneralLogicException;
import com.sermaluc.api.model.PhoneModel;
import com.sermaluc.api.model.UserModel;
import com.sermaluc.api.repository.PhoneRepository;
import com.sermaluc.api.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

public class TestUserService {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp() {

        SignUpRqDto request = new SignUpRqDto();
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setCitycode("7");
        phoneDto.setCountrycode("25");
        phoneDto.setNumber("87650009");

        List listaPhones = new ArrayList<>();
        listaPhones.add(phoneDto);

        request.setEmail("juan@gmail.cl");
        request.setName("juan");
        request.setPassword("a2asfGfdfdf4");
        request.setPhones(listaPhones);

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(null);
        UserModel result = userService.signUp(request);

        assertEquals(request.getName(), result.getName());
        assertEquals(request.getEmail(), result.getEmail());

        Mockito.verify(userRepository, times(1)).save(Mockito.any(UserModel.class));
        Mockito.verify(phoneRepository, times(1)).save(Mockito.any(PhoneModel.class));
    }

    @Test
    public void testSignUpExiste()  {

        SignUpRqDto request = new SignUpRqDto();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(new UserModel());
        assertThrows(GeneralLogicException.class, () -> userService.signUp(request));

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserModel.class));
        Mockito.verify(phoneRepository, Mockito.never()).save(Mockito.any(PhoneModel.class));
    }

}