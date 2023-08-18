package com.social.service.impl;

import com.social.mapper.ChatMapper;
import com.social.repository.ChatRepository;
import com.social.repository.ChatUsersRepository;
import com.social.service.ChatUsersService;
import com.social.service.FollowerService;
import com.social.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {
    @Mock
    ChatRepository chatRepository;
    @Mock
    UserService userService;
    @Mock
    FollowerService followerService;
    @Mock
    ChatUsersService chatUsersService;
    @Mock
    ChatMapper chatMapper;
    @Mock
    ChatUsersRepository chatUsersRepository;

    @InjectMocks
    ChatServiceImpl chatService;
}