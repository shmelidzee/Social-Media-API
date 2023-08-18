package com.social.service.impl;

import com.social.command.CreateNoteCommand;
import com.social.command.UpdateNoteCommand;
import com.social.domain.entities.Note;
import com.social.domain.entities.User;
import com.social.dto.NoteDTO;
import com.social.dto.PageDTO;
import com.social.dto.projection.NoteProjection;
import com.social.exception.ApplicationException;
import com.social.mapper.NoteMapper;
import com.social.repository.NoteRepository;
import com.social.service.NoteService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.social.utils.ExceptionConstants.NOT_HAVE_PERMISSION;
import static com.social.utils.ExceptionUtils.buildApplicationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final UserService userService;
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    /**
     * Delete note
     *
     * @param noteId    - note id
     * @param principal - principal
     * @throws ApplicationException - throw exception if user not admin this note
     */
    @Override
    public void deleteNote(Long noteId, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} try delete note {}", user.getId(), noteId);
        Note note = noteRepository.getReferenceById(noteId);
        isAuthor(user, note);
        noteRepository.delete(note);
    }

    /**
     * Create note
     *
     * @param createNoteCommand - command for create note
     * @param principal         - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public NoteDTO createNote(CreateNoteCommand createNoteCommand, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} create note {}", user.getId(), createNoteCommand);
        Note note = buildNote(createNoteCommand, user);
        noteRepository.save(note);
        return noteMapper.entityToDTO(note);
    }

    /**
     * Update note
     *
     * @param updateNoteCommand - command for update note
     * @param principal         - principal
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public NoteDTO updateNote(UpdateNoteCommand updateNoteCommand, Long noteId, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        log.info("User {} update note with id {}", user.getId(), noteId);
        Note note = noteRepository.getReferenceById(noteId);
        isAuthor(user, note);
        note.setText(updateNoteCommand.getText());
        note.setTitle(updateNoteCommand.getTitle());
        note.setImages(updateNoteCommand.getImagesUrl());
        return noteMapper.entityToDTO(noteRepository.save(note));
    }

    /**
     * Get notes
     *
     * @param needToSortByDate - choose ASC or DESC on sorting by created date
     * @param principal        - principal
     * @param pageable         - pagination for request
     * @throws ApplicationException - throw exception if user not found
     */
    @Override
    public PageDTO<NoteDTO> getNotes(boolean needToSortByDate, Pageable pageable, Principal principal) throws ApplicationException {
        User user = userService.findUserByPrincipal(principal);
        List<NoteProjection> notesProjection;
        if (needToSortByDate) {
            notesProjection = noteRepository.getNotes(user.getId(), true, pageable.getPageSize(), pageable.getPageSize() * pageable.getPageNumber());
        } else {
            notesProjection = noteRepository.getNotes(user.getId(), false, pageable.getPageSize(), pageable.getPageSize() * pageable.getPageNumber());
        }
        List<NoteDTO> notes = notesProjection.stream()
                .map(noteMapper::projectionToDTO)
                .collect(Collectors.toList());
        return new PageDTO<>(notes, pageable.getPageNumber(), pageable.getPageSize(), notes.size());
    }

    private static void isAuthor(User user, Note note) throws ApplicationException {
        if (!note.getAuthor().getId().equals(user.getId())) {
            throw buildApplicationException(HttpStatus.BAD_REQUEST, NOT_HAVE_PERMISSION);
        }
    }

    private Note buildNote(CreateNoteCommand createNoteCommand, User author) {
        return Note.builder()
                .author(author)
                .title(createNoteCommand.getTitle())
                .text(createNoteCommand.getText())
                .images(createNoteCommand.getImagesUrl())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}