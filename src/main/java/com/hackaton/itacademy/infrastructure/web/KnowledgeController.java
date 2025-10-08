package com.hackaton.itacademy.infrastructure.web;

import com.hackaton.itacademy.domain.KnowledgeEntry;
import com.hackaton.itacademy.domain.KnowledgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @GetMapping
    public ResponseEntity<List<KnowledgeEntry>> getAllKnowledge() {
        List<KnowledgeEntry> entries = knowledgeService.getAllKnowledgeEntries();
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<Void> addKnowledgeEntry(@RequestBody KnowledgeEntry entry) {
        knowledgeService.addKnowledgeEntry(entry);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}