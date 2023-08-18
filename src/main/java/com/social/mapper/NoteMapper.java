package com.social.mapper;

import com.social.domain.entities.Note;
import com.social.dto.NoteDTO;
import com.social.dto.projection.NoteProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface NoteMapper {

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorUsername", source = "author.username")
    NoteDTO entityToDTO(Note note);

    NoteDTO projectionToDTO(NoteProjection projection);
}