package com.social.service.impl;

import com.social.domain.entities.Chat;
import com.social.domain.entities.ChatUsers;
import com.social.domain.entities.User;
import com.social.domain.enums.ChatType;
import com.social.dto.ChatDTO;
import com.social.exception.ApplicationException;
import com.social.mapper.ChatMapper;
import com.social.repository.ChatRepository;
import com.social.repository.ChatUsersRepository;
import com.social.service.ChatService;
import com.social.service.ChatUsersService;
import com.social.service.FollowerService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.social.utils.ExceptionConstants.CHAT_ALREADY_EXISTS;
import static com.social.utils.ExceptionConstants.USERS_NOT_FRIENDS;
import static com.social.utils.ExceptionUtils.buildApplicationException;
import static java.time.Instant.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final FollowerService followerService;
    private final ChatUsersService chatUsersService;
    private final ChatMapper chatMapper;
    private final ChatUsersRepository chatUsersRepository;

    /**
     * Create dual chat
     *
     * @param userId    - user id
     * @param principal - principal
     * @throws ApplicationException - throw exception if users is not friends or chats exists
     */
    @Override
    public void createDualChat(Long userId, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        if (!followerService.isFriend(userId, user.getId())) {
            throw buildApplicationException(HttpStatus.BAD_REQUEST, USERS_NOT_FRIENDS);
        }
        if (chatUsersService.existsChatBetweenUsers(userId, user.getId())) {
            throw buildApplicationException(HttpStatus.ALREADY_REPORTED, CHAT_ALREADY_EXISTS);
        }
        log.info("Create dual chat between user {} and user {}", user.getId(), userId);
        Chat chat = buildChat(user, String.format("DUAL CHAT BETWEEN %s AND %s", user.getUsername(), userService.findUserById(userId).getUsername()), ChatType.DUAL);
        chatRepository.save(chat);
        chatUsersRepository.save(buildChatUsers(user, chat));
        chatUsersRepository.save(buildChatUsers(userService.findUserById(userId), chat));
    }

    /**
     * Get my chats
     *
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public List<ChatDTO> getChats(Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("Get chats user: {}", user.getUsername());
        List<Chat> chats = chatRepository.getChats(user.getId());
        return chats.stream()
                .map(chatMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create entity chat
     *
     * @param user - user who make request
     * @param type - chat type
     */
    private Chat buildChat(User user, String name, ChatType type) {
        return Chat.builder()
                .admin(user.getId())
                .type(type)
                .name(name)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    private ChatUsers buildChatUsers(User user, Chat chat) {
        return ChatUsers.builder()
                .user(user)
                .chat(chat)
                .updatedAt(now())
                .createdAt(now())
                .build();
    }
}