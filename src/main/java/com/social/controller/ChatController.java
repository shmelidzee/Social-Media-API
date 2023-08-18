package com.social.controller;

import com.social.dto.ChatDTO;
import com.social.exception.ApplicationException;
import com.social.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
@Tag(name = "Chats", description = "Chat API")
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    @Operation(summary = "Get my chats",
            description = "Get my chats with pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get my chats"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Chats")
    public ResponseEntity<List<ChatDTO>> getMyChats(Principal principal) throws ApplicationException {
        List<ChatDTO> chats = chatService.getChats(principal);
        return ResponseEntity.ok(chats);
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create DUAL chat",
            description = "Create DUAL chat",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Create DUAL chat"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Chats")
    public ResponseEntity<Void> createDualChat(@PathVariable(name = "userId") Long userId,
                                               Principal principal) throws ApplicationException {
        chatService.createDualChat(userId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}