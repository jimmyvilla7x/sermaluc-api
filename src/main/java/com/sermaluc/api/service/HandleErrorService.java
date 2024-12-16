package com.sermaluc.api.service;

import com.sermaluc.api.dto.ErrorRsDto;
import org.springframework.stereotype.Service;

@Service
public class HandleErrorService {
    
 public ErrorRsDto handleError(Exception e) {
        return new ErrorRsDto(e.getMessage());
    }
}
