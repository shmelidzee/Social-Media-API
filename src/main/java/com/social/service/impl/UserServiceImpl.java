package com.social.service.impl;

import com.social.command.RegistrationCommand;
import com.social.domain.entities.User;
import com.social.exception.ApplicationException;
import com.social.mapper.UserMapper;
import com.social.repository.RoleRepository;
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

import static com.social.utils.ExceptionConstants.INCORRECT_REGISTER_PASSWORD;
import static com.social.utils.ExceptionConstants.INVALID_REGISTER_DATA;
import static com.social.utils.ExceptionConstants.USER_NOT_FOUND;
import static com.social.utils.ExceptionUtils.buildApplicationException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final int MAX_SIZE_NAME_AND_PASSWORD = 64;
    private static final int MIN_SIZE_USERNAME_AND_PASSWORD = 3;
    private static final int MIN_LENGTH_PASSWORD = 8;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        return userMapper.mapToUserDetails(user);
    }

    /**
     * Get user details by id
     *
     * @param id - user id
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    @Transactional
    public UserDetails loadUserDetailsByUserId(Long id) throws ApplicationException {
        log.info("Load user by user id: {}", id);
        User user = findUserById(id);
        return userMapper.mapToUserDetails(user);
    }

    /**
     * Get user by id
     *
     * @param userId - user id
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findUserById(Long userId) throws ApplicationException {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Could not find user with id {}", userId);
                    return buildApplicationException(HttpStatus.BAD_REQUEST, USER_NOT_FOUND);
                });
    }

    /**
     * Get user by email
     *
     * @param email - email
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public User findUserByEmail(String email) throws ApplicationException {
        log.info("Get user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> buildApplicationException(HttpStatus.BAD_REQUEST, USER_NOT_FOUND));
    }

    /**
     * Get user by username
     *
     * @param username - username
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public User findUserByUsername(String username) throws ApplicationException {
        log.info("Get user by email: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> buildApplicationException(HttpStatus.BAD_REQUEST, USER_NOT_FOUND));
    }

    /**
     * Check passwords on the same
     *
     * @param user     - user account
     * @param password - password
     */
    @Override
    public boolean matchPassword(User user, String password) {
        log.info("Check password for user {}", user.getEmail());
        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * Register new account
     *
     * @param registrationCommand - data for register
     * @throws ApplicationException - throw exception if data is not valid
     */
    @Override
    public void registerUser(RegistrationCommand registrationCommand) throws ApplicationException {
        log.info("Create new user: {}", registrationCommand);
        validateRegisterCommand(registrationCommand);
        User user = createUser(registrationCommand);
        userRepository.save(user);
    }

    private User createUser(RegistrationCommand command) {
        return User.builder()
                .username(command.getUsername().toLowerCase())
                .email(command.getEmail().toLowerCase())
                .password(passwordEncoder.encode(command.getPassword()))
                .role(roleRepository.findUserRole())
                .build();
    }

    private void validateRegisterCommand(RegistrationCommand command) throws ApplicationException {
        if (command.getUsername().length() < MIN_SIZE_USERNAME_AND_PASSWORD || command.getUsername().length() > MAX_SIZE_NAME_AND_PASSWORD) {
            throw buildApplicationException(HttpStatus.BAD_REQUEST, INVALID_REGISTER_DATA);
        }
        if (command.getEmail().length() < MIN_SIZE_USERNAME_AND_PASSWORD) {
            throw buildApplicationException(HttpStatus.BAD_REQUEST, INVALID_REGISTER_DATA);
        }
        if (command.getPassword().length() < MIN_LENGTH_PASSWORD || command.getPassword().length() > MAX_SIZE_NAME_AND_PASSWORD) {
            throw buildApplicationException(HttpStatus.BAD_REQUEST, INCORRECT_REGISTER_PASSWORD);
        }
    }
}