package com.social.service.impl;

import com.social.repository.FollowerRepository;
import com.social.service.FollowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepository followerRepository;

    @Override
    public boolean isFriend(Long fromUserId, Long toUserId) {
        log.info("Check is friends users with id: {}, {}", fromUserId, toUserId);
        return followerRepository.isFriend(toUserId, fromUserId);
    }
}