package com.social.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteCommand {
    private String title;
    private String text;
    private String[] imagesUrl;
}