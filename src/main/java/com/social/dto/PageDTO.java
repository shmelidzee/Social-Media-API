package com.social.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public final class PageDTO<T> implements Serializable {
    private transient List<T> content;
    private int totalPages;
    private long totalElements;
    private int pageSize;
    private int pageNumber;


    public PageDTO(List<T> content, int pageNumber, int pageSize, int size) {
        this.content = content;
        this.totalPages = size / pageSize;
        this.totalElements = size;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }
}