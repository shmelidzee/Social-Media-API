package com.social.service;

import com.social.command.RegistrationCommand;
import com.social.domain.entities.User;
import com.social.exception.ApplicationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

public interface UserService {

    UserDetails loadUserDetailsByUserId(Long id) throws ApplicationException;

    User findUserById(Long id) throws ApplicationException;

    User findUserByEmail(String email) throws ApplicationException;

    User findUserByUsername(String username) throws ApplicationException;

    boolean matchPassword(User user, String password) throws ApplicationException;

    void registerUser(RegistrationCommand registrationCommand) throws ApplicationException;

    User findUserByPrincipal(Principal principal) throws ApplicationException;
}