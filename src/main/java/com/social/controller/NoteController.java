package com.social.controller;

import com.social.command.CreateNoteCommand;
import com.social.command.UpdateNoteCommand;
import com.social.dto.NoteDTO;
import com.social.dto.PageDTO;
import com.social.exception.ApplicationException;
import com.social.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
@Tag(name = "Notes", description = "Note API")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @Operation(summary = "Create new note/post",
            description = "Create new note/post",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Create new note/post"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Note")
    public ResponseEntity<NoteDTO> createNewNote(@RequestBody CreateNoteCommand createNoteCommand,
                                                 Principal principal) throws ApplicationException {
        NoteDTO note = noteService.createNote(createNoteCommand, principal);
        return ResponseEntity.ok(note);
    }

    @GetMapping
    @Operation(summary = "Get notes",
            description = "Get notes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get notes"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Note")
    public ResponseEntity<PageDTO<NoteDTO>> getTapeOfNotes(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                           @RequestParam(value = "needToSortByDateCreate", required = false, defaultValue = "true") boolean needToSortByDateCreate,
                                                           Principal principal) throws ApplicationException {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        PageDTO<NoteDTO> notes = noteService.getNotes(needToSortByDateCreate, pageable, principal);
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{noteId}")
    @Operation(summary = "Update note",
            description = "Update note",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Update note"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Note")
    public ResponseEntity<NoteDTO> updateMyNote(@RequestBody UpdateNoteCommand updateNoteCommand,
                                                @PathVariable(name = "noteId") Long noteId,
                                                Principal principal) throws ApplicationException {
        NoteDTO note = noteService.updateNote(updateNoteCommand, noteId, principal);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "Delete note",
            description = "Delete note",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delete note"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Note")
    public ResponseEntity<Void> deleteMyNote(@PathVariable(name = "noteId") Long noteId,
                                             Principal principal) throws ApplicationException {
        noteService.deleteNote(noteId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}