package com.social.service.impl;

import com.social.domain.entities.Role;
import com.social.domain.entities.User;
import com.social.exception.ApplicationException;
import com.social.mapper.UserMapper;
import com.social.repository.FollowerRepository;
import com.social.repository.FriendsRepository;
import com.social.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowerServiceImplTest {

    @Mock
    FollowerRepository mockFollowerRepository;
    @Mock
    FriendsRepository mockFriendsRepository;
    @Mock
    UserService mockUserService;
    @Mock
    UserMapper mockUserMapper;
    @Mock
    Pageable mockPageable;
    @Mock
    Principal mockPrincipal;

    @InjectMocks
    FollowerServiceImpl followerService;

    User user = User.builder()
            .id(1L)
            .email("test@gmail.com")
            .username("testusername")
            .password("12341234")
            .role(new Role())
            .build();

    @Test
    void should_return_true_if_users_are_friends() {
        when(mockFriendsRepository.isFriend(1L, 2L)).thenReturn(true);

        assertTrue(followerService.isFriend(2L, 1L));
    }

    @Test
    void should_get_empty_friends() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);
        when(mockPageable.getPageNumber()).thenReturn(1);
        when(mockPageable.getPageSize()).thenReturn(1);
        when(mockFriendsRepository.getFriendsByUserId(1L, 1, 1)).thenReturn(new ArrayList<>());

        assertEquals(0, followerService.getFriends(1L, mockPageable, mockPrincipal).getTotalPages());
    }

    @Test
    void should_get_empty_subscribers() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);
        when(mockPageable.getPageNumber()).thenReturn(1);
        when(mockPageable.getPageSize()).thenReturn(1);
        when(mockFollowerRepository.getSubscribers(1L, 1, 1)).thenReturn(new ArrayList<>());

        assertEquals(0, followerService.getSubscribers(1L, mockPageable, mockPrincipal).getTotalPages());
    }

    @Test
    void should_get_empty_subscribes() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);
        when(mockPageable.getPageNumber()).thenReturn(1);
        when(mockPageable.getPageSize()).thenReturn(1);
        when(mockFollowerRepository.getSubscribes(1L, 1, 1)).thenReturn(new ArrayList<>());

        assertEquals(0, followerService.getSubscribes(1L, mockPageable, mockPrincipal).getTotalPages());
    }
}   