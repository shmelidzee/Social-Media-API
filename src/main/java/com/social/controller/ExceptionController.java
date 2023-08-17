package com.social.controller;

import com.social.dto.UiErrorDTO;
import com.social.exception.ApplicationException;
import com.social.service.ExceptionService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
@Hidden
public class ExceptionController {

    private final ExceptionService exceptionService;

    public ExceptionController(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UiErrorDTO> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        UiErrorDTO uiErrorDto = exceptionService.buildUiErrorDTO(null);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(uiErrorDto);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<UiErrorDTO> handleApplicationException(ApplicationException ex) {
        UiErrorDTO uiErrorDto = exceptionService.buildUiErrorDTO(ex);
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(uiErrorDto);
    }
}