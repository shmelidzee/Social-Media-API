package com.social.repository;

import com.social.domain.entities.Follower;
import com.social.dto.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {

    @Query(value = "SELECT EXISTS (\n" +
            "    SELECT f.id\n" +
            "    FROM Follower f\n" +
            "    WHERE (f.fromUser.id = :fromUserId AND f.toUser.id = :toUserId)\n" +
            "        OR (f.toUser.id = :fromUserId AND f.fromUser.id = :toUserId))" +
            "FROM Follower")
    boolean isFriend(Long toUser, Long fromUser);

    Optional<Follower> getFollowerByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    @Query(value = "select\n" +
            "    u.id as userId,\n" +
            "    u.username\n" +
            "from followers f\n" +
            "         left join users u on u.id = f.from_user\n" +
            "where f.to_user = :userId\n" +
            "limit :lim offset :off", nativeQuery = true)
    List<UserProjection> getSubscribers(Long userId, @Param("lim") Integer limit, @Param("off") Integer offset);

    @Query(value = "select\n" +
            "    u.id as userId,\n" +
            "    u.username\n" +
            "from followers f\n" +
            "         left join users u on u.id = f.to_user\n" +
            "where f.from_user = :userId\n" +
            "limit :lim offset :off", nativeQuery = true)
    List<UserProjection> getSubscribes(Long userId, @Param("lim") Integer limit, @Param("off") Integer offset);
}