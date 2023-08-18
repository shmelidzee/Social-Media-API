package com.social.mapper;

import com.social.domain.entities.Chat;
import com.social.dto.ChatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ChatMapper {

    @Mapping(target = "chatId", source = "id")
    ChatDTO entityToDTO(Chat chat);
}