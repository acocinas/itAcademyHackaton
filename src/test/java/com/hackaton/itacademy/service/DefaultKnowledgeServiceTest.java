package com.hackaton.itacademy.service;

import com.hackaton.itacademy.domain.KnowledgeEntry;
import com.hackaton.itacademy.domain.KnowledgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class DefaultKnowledgeServiceTest {

    @Mock
    private KnowledgeRepository knowledgeRepository;

    @InjectMocks
    private DefaultKnowledgeService knowledgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<KnowledgeEntry> mockEntries = List.of(
                new KnowledgeEntry("Cómo solicito vacaciones?", "Debes enviar un correo a rrhh..."),
                new KnowledgeEntry("Cuál es el horario laboral?", "El horario es de 9:00...")
        );
        when(knowledgeRepository.findAll()).thenReturn(Optional.of(mockEntries));
    }

    @Test
    void shouldFindAnswerWithPartialAndCaseInsensitiveQuestion() {
        String question = "solicito vacaciones";

        Optional<String> result = knowledgeService.findAnswerByQuestion(question);

        assertTrue(result.isPresent());
        assertEquals("Debes enviar un correo a rrhh...", result.get());
    }

    @Test
    void shouldNotFindAnswerForUnknownQuestion() {
        String question = "Pregunta que no existe";

        Optional<String> result = knowledgeService.findAnswerByQuestion(question);

        assertTrue(result.isEmpty());
    }
}