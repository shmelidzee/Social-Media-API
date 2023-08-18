package com.social.service.impl;

import com.social.repository.ChatUsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatUsersServiceImplTest {

    @Mock
    ChatUsersRepository mockChatUserRepository;

    @InjectMocks
    ChatUsersServiceImpl chatUsersService;

    @Test
    void should_return_true_when_users_is_friends() {
        when(mockChatUserRepository.existsChatBetweenUsers(1L, 2L)).thenReturn(true);

        assertTrue(chatUsersService.existsChatBetweenUsers(1L, 2L));
    }
}