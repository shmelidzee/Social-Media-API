package com.social.service.impl;

import com.social.domain.entities.ChatUsers;
import com.social.domain.entities.User;
import com.social.exception.ApplicationException;
import com.social.repository.ChatUsersRepository;
import com.social.service.ChatUsersService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatUsersServiceImpl implements ChatUsersService {

    private final ChatUsersRepository chatUsersRepository;
    private final UserService userService;

    /**
     * Create chat users for users
     *
     * @param userId - user id
     * @param user   - user
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public Set<ChatUsers> saveChatUsers(Long userId, User user) throws ApplicationException {
        Set<ChatUsers> chatUsers = new HashSet<>();
        chatUsers.add(buildChatUsers(user));
        chatUsers.add(buildChatUsers(userService.findUserById(userId)));
        chatUsersRepository.saveAll(chatUsers);
        return chatUsers;
    }

    /**
     * Check exists chat with users
     *
     * @param fromUserId - first user id
     * @param toUserId   - second user id
     */
    @Override
    public boolean existsChatBetweenUsers(Long fromUserId, Long toUserId) {
        log.info("Check exists chat between users with id: {} and {}", fromUserId, toUserId);
        return chatUsersRepository.existsChatBetweenUsers(fromUserId, toUserId);
    }

    /**
     * Create entity
     *
     * @param user - user
     */
    private ChatUsers buildChatUsers(User user) {
        return ChatUsers.builder()
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .user(user)
                .build();
    }
}
