package com.social.repository;

import com.social.domain.entities.ChatUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUsersRepository extends JpaRepository<ChatUsers, Long> {

    @Query(value = "SELECT exists(select CU.CHAT_ID\n" +
            "FROM CHAT_USERS CU\n" +
            "JOIN CHATS C ON CU.CHAT_ID = C.ID\n" +
            "WHERE CU.USER_ID = :toUserId AND CU.CHAT_ID IN (\n" +
            "    SELECT CHAT_ID\n" +
            "    FROM CHAT_USERS\n" +
            "    WHERE USER_ID = :fromUserId))", nativeQuery = true)
    boolean existsChatBetweenUsers(Long fromUserId, Long toUserId);
}