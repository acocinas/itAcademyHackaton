package com.hackaton.itacademy.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.itacademy.domain.KnowledgeEntry;
import com.hackaton.itacademy.domain.KnowledgeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(KnowledgeController.class)
class KnowledgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KnowledgeService knowledgeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllKnowledgeEntries() throws Exception {
        List<KnowledgeEntry> mockEntries = List.of(new KnowledgeEntry("question", "answer"));
        when(knowledgeService.getAllKnowledgeEntries()).thenReturn(mockEntries);

        mockMvc.perform(get("/api/knowledge"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question").value("question"))
                .andExpect(jsonPath("$[0].answer").value("answer"));
    }

    @Test
    void shouldAddKnowledgeEntry() throws Exception {
        KnowledgeEntry newEntry = new KnowledgeEntry("new question", "new answer");
        doNothing().when(knowledgeService).addKnowledgeEntry(newEntry);

        mockMvc.perform(post("/api/knowledge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEntry)))
                .andExpect(status().isCreated());
    }
}