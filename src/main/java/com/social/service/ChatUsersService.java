package com.social.service;

public interface ChatUsersService {

    boolean existsChatBetweenUsers(Long fromUserId, Long toUserId);
}