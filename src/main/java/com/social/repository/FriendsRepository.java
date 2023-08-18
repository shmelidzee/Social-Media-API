package com.social.repository;

import com.social.domain.entities.Friends;
import com.social.dto.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    @Query(value = "SELECT EXISTS (\n" +
            "    SELECT f.id\n" +
            "    FROM Friends f\n" +
            "    WHERE ((f.fromUser.id = :fromUser AND f.toUser.id = :toUser)\n" +
            "        OR (f.toUser.id = :fromUser AND f.fromUser.id = :toUser))" +
            "AND f.accepted = true)" +
            "FROM Friends ")
    boolean isFriend(Long toUser, Long fromUser);

    @Query(value = "from Friends f where (f.fromUser.id = :fromUserId and f.toUser.id = :toUserId) or (f.fromUser.id = :toUserId and f.toUser.id = :fromUserId)")
    Optional<Friends> getByFromUserId(Long fromUserId, Long toUserId);

    @Query(value = "from Friends f where (f.fromUser.id = :fromUserId and f.toUser.id = :toUserId)")
    Optional<Friends> getRequestFromUser(Long fromUserId, Long toUserId);

    @Query(value = "select (case\n" +
            "            when f.from_user = :userId then f.to_user\n" +
            "            when f.to_user = :userId then f.from_user end) as userId,\n" +
            "       (case\n" +
            "            when f.from_user = :userId then u.username\n" +
            "            when f.to_user = :userId then u2.username end) as username\n" +
            "from friends f\n" +
            "         left join users u on u.id = f.from_user\n" +
            "         left join users u2 on u2.id = f.to_user\n" +
            "where (f.from_user = :userId or f.to_user = :userId)\n" +
            "  and f.accepted = true\n" +
            "limit :lim offset :off", nativeQuery = true)
    List<UserProjection> getFriendsByUserId(Long userId, @Param("lim") Integer limit, @Param("off") Integer offset);
}