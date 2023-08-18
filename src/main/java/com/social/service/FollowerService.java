package com.social.service;

public interface FollowerService {

    boolean isFriend(Long fromUserId, Long toUserId);
}
