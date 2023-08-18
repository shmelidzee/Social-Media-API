package com.social.service;

import com.social.domain.entities.ChatUsers;
import com.social.domain.entities.User;
import com.social.exception.ApplicationException;

import java.util.Set;

public interface ChatUsersService {

    Set<ChatUsers> saveChatUsers(Long userId, User user) throws ApplicationException;

    boolean existsChatBetweenUsers(Long fromUserId, Long toUserId);
}