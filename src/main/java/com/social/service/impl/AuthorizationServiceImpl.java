package com.social.service.impl;

import com.social.command.LoginCommand;
import com.social.domain.entities.User;
import com.social.dto.UserLoginDTO;
import com.social.exception.ApplicationException;
import com.social.mapper.UserMapper;
import com.social.security.JwtTokenProvider;
import com.social.service.AuthorizationService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.social.utils.ExceptionConstants.INCORRECT_LOGIN_OR_PASSWORD;
import static com.social.utils.ExceptionUtils.buildApplicationException;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenGenerator;
    private final UserMapper userMapper;

    @Override
    public UserLoginDTO login(LoginCommand loginCommand) throws ApplicationException {
        User user;
        if (nonNull(loginCommand.getEmail())) {
            user = userService.findUserByEmail(loginCommand.getEmail());
        } else {
            user = userService.findUserByUsername(loginCommand.getUsername());
        }
        if (!userService.matchPassword(user, loginCommand.getPassword())) {
            log.error("Provided password is incorrect for user {}", user.getEmail());
            throw buildApplicationException(HttpStatus.FORBIDDEN, INCORRECT_LOGIN_OR_PASSWORD);
        }
        String accessToken = jwtTokenGenerator.generateToken(user);
        return userMapper.loginToDTO(user, accessToken);
    }
}