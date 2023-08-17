package com.social.service.impl;

import com.social.command.LoginCommand;
import com.social.dto.UserLoginDTO;
import com.social.service.AuthorizationService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserService userService;

    @Override
    public UserLoginDTO login(LoginCommand loginCommand) {
        return null;
    }
}