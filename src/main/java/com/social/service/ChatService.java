package com.social.service;

import com.social.dto.ChatDTO;
import com.social.exception.ApplicationException;

import java.security.Principal;
import java.util.List;

public interface ChatService {

    void createDualChat(Long userId, Principal principal) throws ApplicationException;

    List<ChatDTO> getChats(Principal principal) throws ApplicationException;
}