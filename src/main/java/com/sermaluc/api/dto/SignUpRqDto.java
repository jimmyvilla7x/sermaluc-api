package com.sermaluc.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpRqDto {
    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;

}