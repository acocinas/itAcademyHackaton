package com.hackaton.itacademy.service;

import com.hackaton.itacademy.domain.KnowledgeEntry;
import com.hackaton.itacademy.domain.KnowledgeRepository;
import com.hackaton.itacademy.domain.KnowledgeService;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultKnowledgeService implements KnowledgeService {

    private final KnowledgeRepository knowledgeRepository;

    public DefaultKnowledgeService(KnowledgeRepository knowledgeRepository) {
        this.knowledgeRepository = knowledgeRepository;
    }

    @Override
    public Optional<String> findAnswerByQuestion(String userQuestion) {
        String normalizedUserQuestion = normalizeString(userQuestion);

        Optional<List<KnowledgeEntry>> optionalEntries = knowledgeRepository.findAll();

        if (optionalEntries.isPresent()) {
            List<KnowledgeEntry> entries = optionalEntries.get();
            for (KnowledgeEntry entry : entries) {
                String normalizedEntryQuestion = normalizeString(entry.getQuestion());
                if (normalizedEntryQuestion.contains(normalizedUserQuestion)) {
                    return Optional.of(entry.getAnswer());
                }
            }
        }

        return Optional.empty();
    }

    private String normalizeString(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}", "")
                .toLowerCase();
    }

    @Override
    public void addKnowledgeEntry(KnowledgeEntry entry) {
        Optional<List<KnowledgeEntry>> optionalEntries = knowledgeRepository.findAll();
        List<KnowledgeEntry> entries = optionalEntries.orElse(List.of());

        entries.add(entry);
        knowledgeRepository.saveAll(entries);
    }

    @Override
    public List<KnowledgeEntry> getAllKnowledgeEntries() {
        return knowledgeRepository.findAll().orElse(List.of());
    }
}