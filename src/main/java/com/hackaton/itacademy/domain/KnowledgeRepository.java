package com.hackaton.itacademy.domain;

import java.util.List;
import java.util.Optional;

public interface KnowledgeRepository {

    Optional<List<KnowledgeEntry>> findAll();

    void saveAll(List<KnowledgeEntry> knowledgeEntries);
}