package com.social.repository;

import com.social.domain.entities.ChatUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUsersRepository extends JpaRepository<ChatUsers, Long> {
}
