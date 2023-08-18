package com.social.service;

import com.social.dto.ChatDTO;
import com.social.exception.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.security.Principal;

public interface ChatService {

    void createDualChat(Long userId, Principal principal) throws ApplicationException;

    Page<ChatDTO> getChats(PageRequest pageable, Principal principal) throws ApplicationException;
}