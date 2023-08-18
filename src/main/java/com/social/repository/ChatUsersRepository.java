package com.social.repository;

import com.social.domain.entities.ChatUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUsersRepository extends JpaRepository<ChatUsers, Long> {

    @Query(value = "select exists (select c.id " +
            "from Chat c " +
            "join ChatUsers cu on c.id = cu.chat.id " +
            "where c.chatUsers in (:fromUserId, :toUserId))" +
            "from Chat")
    boolean existsChatBetweenUsers(Long fromUserId, Long toUserId);
}