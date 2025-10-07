package com.hackaton.itacademy.domain;

import java.util.List;
import java.util.Optional;

public interface KnowledgeService {

    Optional<String> findAnswerByQuestion(String question);

    void addKnowledgeEntry(KnowledgeEntry entry);

    List<KnowledgeEntry> getAllKnowledgeEntries();
}