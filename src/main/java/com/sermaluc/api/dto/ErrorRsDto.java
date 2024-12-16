package com.sermaluc.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorRsDto {
    private String mensaje;

    public ErrorRsDto(String mensaje) {
        this.mensaje = mensaje;
    }
}
