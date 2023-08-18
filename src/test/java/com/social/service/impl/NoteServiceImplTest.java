package com.social.service.impl;

import com.social.command.CreateNoteCommand;
import com.social.domain.entities.Note;
import com.social.domain.entities.Role;
import com.social.domain.entities.User;
import com.social.dto.NoteDTO;
import com.social.exception.ApplicationException;
import com.social.mapper.NoteMapper;
import com.social.repository.NoteRepository;
import com.social.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    UserService mockUserService;
    @Mock
    NoteRepository mockNoteRepository;
    @Mock
    NoteMapper mockNoteMapper;
    @Mock
    Principal mockPrincipal;
    @Mock
    Pageable mockPageable;

    @InjectMocks
    NoteServiceImpl noteService;

    User user = User.builder()
            .id(1L)
            .email("test@gmail.com")
            .username("testusername")
            .password("12341234")
            .role(new Role())
            .build();
    Note note = new Note(1L, "test", "test", new String[]{"first"}, Instant.now(), Instant.now(), user);

    CreateNoteCommand createNoteCommand = new CreateNoteCommand("test", "test", new String[]{"first"});

    NoteDTO noteDTO = new NoteDTO(1L, "test", "test", new String[]{"first"}, 1L, "testusername", "");

    @Test
    void should_delete_note() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);
        when(mockNoteRepository.getReferenceById(1L)).thenReturn(note);

        assertDoesNotThrow(() -> noteService.deleteNote(1L, mockPrincipal));
    }

    @Test
    void should_create_note() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);
        when(mockNoteMapper.entityToDTO(any())).thenReturn(noteDTO);

        assertEquals(1L, noteService.createNote(createNoteCommand, mockPrincipal).getId());
    }

    @Test
    void should_get_empty_notes() throws ApplicationException {
        when(mockUserService.findUserByPrincipal(mockPrincipal)).thenReturn(user);
        when(mockPageable.getPageNumber()).thenReturn(1);
        when(mockPageable.getPageSize()).thenReturn(1);
        when(mockNoteRepository.getNotes(1L, true, 1, 1)).thenReturn(new ArrayList<>());

        assertEquals(0, noteService.getNotes(true, mockPageable, mockPrincipal).getTotalPages());
    }
}