package com.social.service.impl;

import com.social.domain.entities.Follower;
import com.social.domain.entities.Friends;
import com.social.domain.entities.User;
import com.social.dto.PageDTO;
import com.social.dto.UserDTO;
import com.social.dto.projection.UserProjection;
import com.social.exception.ApplicationException;
import com.social.mapper.UserMapper;
import com.social.repository.FollowerRepository;
import com.social.repository.FriendsRepository;
import com.social.service.FollowerService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.social.utils.ExceptionConstants.NOT_FOUND;
import static com.social.utils.ExceptionUtils.buildApplicationException;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepository followerRepository;
    private final FriendsRepository friendsRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Check users on is friends
     *
     * @param fromUserId - first user id
     * @param toUserId   - second user id
     */
    @Override
    public boolean isFriend(Long fromUserId, Long toUserId) {
        log.info("Check is friends users with id: {}, {}", fromUserId, toUserId);
        return friendsRepository.isFriend(toUserId, fromUserId);
    }

    /**
     * Unfollow from user and delete his from friend
     *
     * @param userId    - user id for unfollowing
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public void unfollowUser(Long userId, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} unfollow from {}", user.getId(), userId);
        Follower follower = getFollowerEntity(userId, user);
        followerRepository.delete(follower);
        Optional<Friends> friend = friendsRepository.getRequestFromUser(user.getId(), userId);
        if (friend.isPresent()) {
            friendsRepository.delete(friend.get());
        } else {
            Friends friends = getFriendsEntity(userId, user);
            friends.setAccepted(false);
            friends.setRead(true);
            friendsRepository.save(friends);
        }
    }

    /**
     * Reject user request on friends
     *
     * @param userId    - who send request on friend
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public void rejectRequestInFriend(Long userId, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} reject invite to friends", user.getId());
        Friends friends = getFriendsEntity(userId, user);
        friends.setAccepted(false);
        friends.setRead(true);
        friendsRepository.save(friends);
    }

    /**
     * Accept user request on friends
     *
     * @param userId    - who send request on friend
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public void acceptInvite(Long userId, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} accept invite to friends", user.getId());
        Friends friends = getFriendsEntity(userId, user);
        friends.setAccepted(true);
        friends.setRead(true);
        friendsRepository.save(friends);
        Follower follower = buildFollower(userService.findUserById(userId), user);
        followerRepository.save(follower);
    }

    /**
     * Send request to friend
     *
     * @param userId    - whom to send request on friend
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public void sentRequestToFriend(Long userId, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} follow to {}", user.getId(), userId);
        Follower follower = buildFollower(userService.findUserById(userId), user);
        followerRepository.save(follower);
        Optional<Friends> optionalFriends = friendsRepository.getByFromUserId(userId, user.getId());
        Friends friends;
        if (optionalFriends.isEmpty()) {
            friends = buildReuestsToFriends(userId, user);
        } else {
            friends = optionalFriends.get();
            friends.setAccepted(true);
            friends.setRead(true);
        }
        friendsRepository.save(friends);
    }

    /**
     * Get subscribers
     *
     * @param userId    - user id which get subscribers
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public PageDTO<UserDTO> getSubscribers(Long userId, Pageable pageable, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} get subscribers", user.getId());
        List<UserProjection> userProjections;
        if (nonNull(userId)) {
            userProjections = followerRepository.getSubscribers(userId, pageable.getPageSize(), pageable.getPageSize() * pageable.getPageNumber());
        } else {
            userProjections = followerRepository.getSubscribers(user.getId(), pageable.getPageSize(), pageable.getPageSize() * pageable.getPageNumber());
        }
        List<UserDTO> users = userProjections.stream()
                .map(userMapper::projectionToDTO)
                .collect(Collectors.toList());
        return new PageDTO<>(users, pageable.getPageNumber(), pageable.getPageSize(), users.size());
    }

    /**
     * Get subscribes
     *
     * @param userId    - user id which get subscribes
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public PageDTO<UserDTO> getSubscribes(Long userId, Pageable pageable, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} get subscribes", user.getId());
        List<UserProjection> userProjections;
        if (nonNull(userId)) {
            userProjections = followerRepository.getSubscribes(userId, pageable.getPageSize(), pageable.getPageSize() * pageable.getPageNumber());
        } else {
            userProjections = followerRepository.getSubscribes(user.getId(), pageable.getPageSize(), pageable.getPageSize() * pageable.getPageNumber());
        }
        List<UserDTO> users = userProjections.stream()
                .map(userMapper::projectionToDTO)
                .collect(Collectors.toList());
        return new PageDTO<>(users, pageable.getPageNumber(), pageable.getPageSize(), users.size());
    }

    /**
     * Get friends
     *
     * @param userId    - user id which get friends
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public PageDTO<UserDTO> getFriends(Long userId, Pageable pageable, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} get friends", user.getId());
        List<UserProjection> userProjections;
        if (nonNull(userId)) {
            userProjections = friendsRepository.getFriendsByUserId(userId, pageable.getPageSize(), pageable.getPageSize() * pageable.getPageNumber());
        } else {
            userProjections = friendsRepository.getFriendsByUserId(user.getId(), pageable.getPageSize(), pageable.getPageSize() * pageable.getPageNumber());
        }
        List<UserDTO> users = userProjections.stream()
                .map(userMapper::projectionToDTO)
                .collect(Collectors.toList());
        return new PageDTO<>(users, pageable.getPageNumber(), pageable.getPageSize(), users.size());
    }

    private Friends getFriendsEntity(Long userId, User user) throws ApplicationException {
        log.info("Get friends entity between {} and {}", userId, user.getId());
        return friendsRepository.getByFromUserId(userId, user.getId())
                .orElseThrow(() -> buildApplicationException(HttpStatus.BAD_REQUEST, NOT_FOUND));
    }

    private Follower getFollowerEntity(Long userId, User user) throws ApplicationException {
        log.info("Get follower entity from {} to {}", user.getId(), userId);
        return followerRepository.getFollowerByFromUserIdAndToUserId(user.getId(), userId)
                .orElseThrow(() -> buildApplicationException(HttpStatus.BAD_REQUEST, NOT_FOUND));
    }

    private Follower buildFollower(User toUser, User fromUser) {
        return Follower.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }

    private Friends buildReuestsToFriends(Long toUserId, User fromUser) throws ApplicationException {
        return Friends.builder()
                .toUser(userService.findUserById(toUserId))
                .fromUser(fromUser)
                .accepted(false)
                .build();
    }
}