package com.social.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails loadUserDetailsByUserId(Long id);
}