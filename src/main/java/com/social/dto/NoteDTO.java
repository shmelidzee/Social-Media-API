package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteDTO {
    private Long id;
    private String title;
    private String text;
    private String[] images;
    private Long authorId;
    private String authorUsername;
    private String createAt;
}