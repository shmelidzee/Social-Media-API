package com.social.controller;

import com.social.command.RegistrationCommand;
import com.social.exception.ApplicationException;
import com.social.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Register user",
            description = "Register user",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Get info about parcel"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "User")
    public ResponseEntity<Void> registration(@RequestParam("registrationCommand")
                                             RegistrationCommand registrationCommand) throws ApplicationException {
        userService.registerUser(registrationCommand);
        return ResponseEntity.ok().build();
    }
}