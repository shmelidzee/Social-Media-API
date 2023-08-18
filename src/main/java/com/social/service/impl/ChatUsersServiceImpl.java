package com.social.service.impl;

import com.social.repository.ChatUsersRepository;
import com.social.service.ChatUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatUsersServiceImpl implements ChatUsersService {

    private final ChatUsersRepository chatUsersRepository;

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
}