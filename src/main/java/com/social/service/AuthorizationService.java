package com.social.service;

import com.social.command.LoginCommand;
import com.social.dto.UserLoginDTO;

public interface AuthorizationService {

    UserLoginDTO login(LoginCommand loginCommand);
}