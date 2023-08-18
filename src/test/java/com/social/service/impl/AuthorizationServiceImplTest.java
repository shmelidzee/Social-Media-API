package com.social.service.impl;

import com.social.command.LoginCommand;
import com.social.domain.entities.Role;
import com.social.domain.entities.User;
import com.social.dto.UserLoginDTO;
import com.social.exception.ApplicationException;
import com.social.mapper.UserMapper;
import com.social.security.JwtTokenProvider;
import com.social.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceImplTest {

    @Mock
    UserService mockUserService;
    @Mock
    JwtTokenProvider mockJwtTokenGenerator;
    @Mock
    UserMapper mockUserMapper;

    @InjectMocks
    AuthorizationServiceImpl authorizationService;

    User user = User.builder()
            .id(1L)
            .email("test@gmail.com")
            .username("testusername")
            .password("12341234")
            .role(new Role())
            .build();

    LoginCommand loginCommand = new LoginCommand("test@gmail.com", null, "12341234");
    LoginCommand loginCommandWithUsername = new LoginCommand(null, "testusername", "1234123432");

    @Test
    void should_throw_exception_if_user_not_found_by_email() throws ApplicationException {
        when(mockUserService.findUserByEmail("test@gmail.com")).thenThrow(ApplicationException.class);

        assertThrows(ApplicationException.class, () -> authorizationService.login(loginCommand));
    }

    @Test
    void should_throw_exception_if_user_not_found_by_username() throws ApplicationException {
        when(mockUserService.findUserByUsername("testusername")).thenThrow(ApplicationException.class);

        assertThrows(ApplicationException.class, () -> authorizationService.login(loginCommandWithUsername));
    }

    @Test
    void should_throw_exception_if_password_was_incorrect() throws ApplicationException {
        when(mockUserService.findUserByUsername("testusername")).thenReturn(user);

        assertThrows(ApplicationException.class, () -> authorizationService.login(loginCommandWithUsername));
    }

    @Test
    void should_get_user() throws ApplicationException {
        when(mockUserService.findUserByUsername("testusername")).thenReturn(user);
        when(mockUserService.matchPassword(user, "1234123432")).thenReturn(true);
        when(mockJwtTokenGenerator.generateToken(user)).thenReturn("Test token");
        when(mockUserMapper.loginToDTO(user, "Test token")).thenReturn(new UserLoginDTO(1L, "test", "test", "ROLE_USER", "Test token"));

        assertEquals("Test token", authorizationService.login(loginCommandWithUsername).getAccessToken());
    }
}