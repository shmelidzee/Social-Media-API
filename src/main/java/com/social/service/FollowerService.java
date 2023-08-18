package com.social.service;

import com.social.dto.PageDTO;
import com.social.dto.UserDTO;
import com.social.exception.ApplicationException;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface FollowerService {

    boolean isFriend(Long fromUserId, Long toUserId);

    void unfollowUser(Long userId, Principal principal) throws ApplicationException;

    void rejectRequestInFriend(Long userId, Principal principal) throws ApplicationException;

    void acceptInvite(Long userId, Principal principal) throws ApplicationException;

    void sentRequestToFriend(Long userId, Principal principal) throws ApplicationException;

    PageDTO<UserDTO> getSubscribers(Long userId, Pageable pageable, Principal principal) throws ApplicationException;

    PageDTO<UserDTO> getSubscribes(Long userId, Pageable pageable, Principal principal) throws ApplicationException;

    PageDTO<UserDTO> getFriends(Long userId, Pageable pageable, Principal principal) throws ApplicationException;
}