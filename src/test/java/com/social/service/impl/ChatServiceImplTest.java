package com.social.service.impl;

import com.social.domain.entities.Role;
import com.social.domain.entities.User;
import com.social.exception.ApplicationException;
import com.social.mapper.ChatMapper;
import com.social.repository.ChatRepository;
import com.social.repository.ChatUsersRepository;
import com.social.service.ChatUsersService;
import com.social.service.FollowerService;
import com.social.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {
    @Mock
    ChatRepository mockChatRepository;
    @Mock
    UserService mockUserService;
    @Mock
    FollowerService mockFollowerService;
    @Mock
    ChatUsersService mockChatUsersService;
    @Mock
    ChatMapper mockChatMapper;
    @Mock
    ChatUsersRepository mockChatUsersRepository;
    @Mock
    Principal mockPrincipal;

    @InjectMocks
    ChatServiceImpl chatService;

    User user = User.builder()
            .id(1L)
            .email("test@gmail.com")
            .username("testusername")
            .password("12341234")
            .role(new Role())
            .build();

    @Test
    void should_get_empty_chats() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);
        when(mockChatRepository.getChats(1L)).thenReturn(new ArrayList<>());

        assertEquals(0, chatService.getChats(mockPrincipal).size());
    }

    @Test
    void should_throw_exception_if_users_are_not_friends() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);

        assertThrows(ApplicationException.class, () -> chatService.createDualChat(2L, mockPrincipal));
    }

    @Test
    void should_throw_exception_if_chat_exists() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);
        when(mockFollowerService.isFriend(2L, 1L)).thenReturn(true);
        when(mockChatUsersService.existsChatBetweenUsers(2L, 1L)).thenReturn(true);

        assertThrows(ApplicationException.class, () -> chatService.createDualChat(2L, mockPrincipal));
    }
}