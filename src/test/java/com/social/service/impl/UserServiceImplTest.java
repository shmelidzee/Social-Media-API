package com.social.service.impl;

import com.social.command.RegistrationCommand;
import com.social.domain.entities.Role;
import com.social.domain.entities.User;
import com.social.exception.ApplicationException;
import com.social.mapper.UserMapper;
import com.social.repository.RoleRepository;
import com.social.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository mockUserRepository;
    @Mock
    RoleRepository mockRoleRepository;
    @Mock
    PasswordEncoder mockPasswordEncoder;
    @Mock
    UserMapper mockUserMapper;
    @Mock
    Principal mockPrincipal;

    @InjectMocks
    UserServiceImpl userService;

    User user = User.builder()
            .id(1L)
            .email("test@gmail.com")
            .username("testusername")
            .password("12341234")
            .role(new Role())
            .build();

    RegistrationCommand registrationCommandWithInvalidUsername = new RegistrationCommand("t", "test@gmail.com", "12341234");
    RegistrationCommand registrationCommandWithInvalidEmail = new RegistrationCommand("test", "om", "12341234");
    RegistrationCommand registrationCommandWithInvalidPassword = new RegistrationCommand("test", "test@gmail.com", "1234");

    @Test
    void should_throw_exception_if_username_not_valid() {
        assertThrows(ApplicationException.class, () -> userService.registerUser(registrationCommandWithInvalidUsername));
    }

    @Test
    void should_throw_exception_if_email_not_valid() {
        assertThrows(ApplicationException.class, () -> userService.registerUser(registrationCommandWithInvalidEmail));
    }

    @Test
    void should_throw_exception_if_password_not_valid() {
        assertThrows(ApplicationException.class, () -> userService.registerUser(registrationCommandWithInvalidPassword));
    }

    @Test
    void should_get_user_by_principal() throws ApplicationException {
        when(mockPrincipal.getName()).thenReturn("testName");
        when(mockUserRepository.findByUsername("testName")).thenReturn(Optional.ofNullable(user));

        assertEquals(1L, userService.findUserByPrincipal(mockPrincipal).getId());
    }

    @Test
    void should_throw_exception_if_user_not_found_by_principal() {
        when(mockPrincipal.getName()).thenReturn("testName");
        when(mockUserRepository.findByUsername("testName")).thenReturn(Optional.empty());

        assertThrows(ApplicationException.class, () -> userService.findUserByPrincipal(mockPrincipal));
    }

    @Test
    void should_return_true_if_passwords_the_same() {
        when(mockPasswordEncoder.matches(user.getPassword(), "12341234")).thenReturn(true);

        assertTrue(userService.matchPassword(user, "12341234"));
    }

    @Test
    void should_get_user_by_email() throws ApplicationException {
        when(mockUserRepository.findByEmail("test@gmail.com")).thenReturn(Optional.ofNullable(user));

        assertEquals(1L, userService.findUserByEmail("test@gmail.com").getId());
    }

    @Test
    void should_throw_exception_if_user_not_found_by_id() {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApplicationException.class, () -> userService.findUserById(1L));
    }

    @Test
    void should_throw_exception_if_user_not_found_by_username() {
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(ApplicationException.class, () -> userService.findUserByUsername("username"));
    }

    @Test
    void should_throw_exception_if_user_not_found_by_id_in_load() {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApplicationException.class, () -> userService.loadUserDetailsByUserId(1L));
    }

    @Test
    void should_get_user_by_id_in_load() throws ApplicationException {
        when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(mockUserMapper.mapToUserDetails(user)).thenReturn(null);

        assertNull(userService.loadUserDetailsByUserId(1L));
    }
}