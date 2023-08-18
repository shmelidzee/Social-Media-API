package com.social.service;

import com.social.command.CreateNoteCommand;
import com.social.command.UpdateNoteCommand;
import com.social.dto.NoteDTO;
import com.social.dto.PageDTO;
import com.social.exception.ApplicationException;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface NoteService {

    void deleteNote(Long noteId, Principal principal) throws ApplicationException;

    NoteDTO createNote(CreateNoteCommand createNoteCommand, Principal principal) throws ApplicationException;

    NoteDTO updateNote(UpdateNoteCommand updateNoteCommand, Long noteId, Principal principal) throws ApplicationException;

    PageDTO<NoteDTO> getNotes(boolean needToSortByDate, Pageable pageable, Principal principal) throws ApplicationException;
}