package com.social.controller;

import com.social.command.LoginCommand;
import com.social.dto.UserLoginDTO;
import com.social.service.AuthorizationService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authorization", description = "Auth API")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Timed("authorization.login")
    @CrossOrigin
    @PostMapping("/login")
    @Operation(description = "Login", tags = "Authorization")
    public ResponseEntity<UserLoginDTO> login(@RequestBody @Valid LoginCommand loginCommand) {
        UserLoginDTO user = authorizationService.login(loginCommand);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}