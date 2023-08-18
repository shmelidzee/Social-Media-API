package com.social.repository;

import com.social.domain.entities.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {


    @Query(value = "SELECT EXISTS (\n" +
            "    SELECT f.id\n" +
            "    FROM Follower f\n" +
            "    WHERE (f.fromUser.id = :fromUserId AND f.toUser.id = :toUserId)\n" +
            "        OR (f.toUser.id = :fromUserId AND f.fromUser.id = :toUserId))" +
            "FROM Follower")
    boolean isFriend(Long toUser, Long fromUser);
}
