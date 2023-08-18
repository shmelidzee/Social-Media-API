package com.social.dto;

import com.social.domain.enums.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDTO {
    private Long chatId;
    private String name;
    private ChatType type;
    private Instant createdAt;
    private Instant updatedAt;
}