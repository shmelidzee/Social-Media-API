package com.social.repository;

import com.social.domain.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("from Chat c where :userId in (select cu.user.id from ChatUsers cu where cu.chat.id = c.id)")
    List<Chat> getChats(Long userId);
}