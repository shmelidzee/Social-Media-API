package com.social.controller;

import com.social.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
    public ResponseEntity<?> getMyChats(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                        Principal principal) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create DUAL chat",
            description = "Create DUAL chat",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Create DUAL chat"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Chats")
    public ResponseEntity<?> createDualChat(@PathVariable(name = "userId") Long userId,
                                            Principal principal) {
        return ResponseEntity.ok(null);
    }
}