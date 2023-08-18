package com.social.dto.projection;

public interface NoteProjection {

    Long getId();

    String getTitle();

    String getText();

    String[] getImages();

    Long getAuthorId();

    String getAuthorUsername();

    String getCreatedAt();
}