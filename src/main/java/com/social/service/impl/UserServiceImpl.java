package com.social.service.impl;

import com.social.domain.entities.User;
import com.social.exception.ApplicationException;
import com.social.mapper.UserMapper;
import com.social.repository.UserRepository;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.social.utils.ExceptionConstants.USER_NOT_FOUND;
import static com.social.utils.ExceptionUtils.buildApplicationException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        return userMapper.mapToUserDetails(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserDetailsByUserId(Long id) throws ApplicationException {
        log.info("Load user by user id: {}", id);
        User user = findUserById(id);
        return userMapper.mapToUserDetails(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findUserById(Long userId) throws ApplicationException {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Could not find user with id {}", userId);
                    return buildApplicationException(HttpStatus.BAD_REQUEST, USER_NOT_FOUND);
                });
    }

    @Override
    public User findUserByEmail(String email) throws ApplicationException {
        log.info("Get user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> buildApplicationException(HttpStatus.BAD_REQUEST, USER_NOT_FOUND));
    }

    @Override
    public User findUserByUsername(String username) throws ApplicationException {
        log.info("Get user by email: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> buildApplicationException(HttpStatus.BAD_REQUEST, USER_NOT_FOUND));
    }

    @Override
    public boolean matchPassword(User user, String password) {
        log.info("Check password for user {}", user.getEmail());
        return passwordEncoder.matches(password, user.getPassword());
    }
}