package com.social.service.impl;

import com.social.domain.entities.Chat;
import com.social.domain.entities.User;
import com.social.domain.enums.ChatType;
import com.social.dto.ChatDTO;
import com.social.exception.ApplicationException;
import com.social.repository.ChatRepository;
import com.social.service.ChatService;
import com.social.service.ChatUsersService;
import com.social.service.FollowerService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;

import static com.social.utils.ExceptionConstants.CHAT_ALREADY_EXISTS;
import static com.social.utils.ExceptionConstants.USERS_NOT_FRIENDS;
import static com.social.utils.ExceptionUtils.buildApplicationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final FollowerService followerService;
    private final ChatUsersService chatUsersService;

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
        chatRepository.save(buildChat(userId, user, ChatType.DUAL));
    }

    @Override
    public Page<ChatDTO> getChats(PageRequest pageable, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("Get chats user: {}", user.getUsername());
        //переписать получение чатов
        return null;
    }

    private Chat buildChat(Long userId, User user, ChatType type) throws ApplicationException {
        return Chat.builder()
                .admin(user.getId())
                .type(type)
                .chatUsers(chatUsersService.saveChatUsers(userId, user))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .name(null)
                .build();
    }
}