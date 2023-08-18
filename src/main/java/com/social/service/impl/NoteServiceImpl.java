package com.social.service.impl;

import com.social.command.CreateNoteCommand;
import com.social.command.UpdateNoteCommand;
import com.social.domain.entities.Note;
import com.social.domain.entities.User;
import com.social.dto.NoteDTO;
import com.social.exception.ApplicationException;
import com.social.repository.NoteRepository;
import com.social.service.NoteService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static com.social.utils.ExceptionConstants.NOT_HAVE_PERMISSION;
import static com.social.utils.ExceptionUtils.buildApplicationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final UserService userService;
    private final NoteRepository noteRepository;

    @Override
    public void deleteNote(Long noteId, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} try delete note {}", user.getId(), noteId);
        Note note = noteRepository.getReferenceById(noteId);
        if (!note.getAuthor().getId().equals(user.getId())) {
            throw buildApplicationException(HttpStatus.BAD_REQUEST, NOT_HAVE_PERMISSION);
        }
        noteRepository.delete(note);
    }

    @Override
    public NoteDTO createNote(CreateNoteCommand createNoteCommand, Principal principal) {
        return null;
    }

    @Override
    public NoteDTO updateNote(UpdateNoteCommand updateNoteCommand, Long noteId, Principal principal) {
        return null;
    }
}
