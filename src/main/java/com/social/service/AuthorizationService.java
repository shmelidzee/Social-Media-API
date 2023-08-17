package com.social.service;

import com.social.command.LoginCommand;
import com.social.dto.UserLoginDTO;
import com.social.exception.ApplicationException;

public interface AuthorizationService {

    UserLoginDTO login(LoginCommand loginCommand) throws ApplicationException;
}