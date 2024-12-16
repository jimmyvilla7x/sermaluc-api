package com.sermaluc.api.service;

import com.sermaluc.api.dto.ErrorRsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHandleErrorService {
    @InjectMocks
    private HandleErrorService handleErrorService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleError() {
        String errorMessage = "Error message";
        Exception testException = new Exception(errorMessage);

        ErrorRsDto result = handleErrorService.handleError(testException);
        assertEquals(errorMessage, result.getMensaje());
    }
}