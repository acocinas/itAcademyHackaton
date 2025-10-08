package com.hackaton.itacademy.infrastructure.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.itacademy.domain.KnowledgeEntry;
import com.hackaton.itacademy.domain.KnowledgeRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class JsonKnowledgeRepository implements KnowledgeRepository {

    private final ObjectMapper objectMapper;
    private List<KnowledgeEntry> knowledgeEntries;

    @Value("classpath:knowledge.json")
    private Resource knowledgeFile;

    public JsonKnowledgeRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void loadKnowledgeFromFile() {
        try {
            this.knowledgeEntries = objectMapper.readValue(knowledgeFile.getInputStream(), new TypeReference<>() {});
        } catch (IOException e) {
            this.knowledgeEntries = List.of();
            log.error("No se pudo cargar el archivo knowledge.json. El bot comenzará vacío. Error: {}", e.getMessage());
        }
    }

    @Override
    public Optional<List<KnowledgeEntry>> findAll() {
        return Optional.ofNullable(knowledgeEntries);
    }

    @Override
    public void saveAll(List<KnowledgeEntry> entriesToSave) {
        this.knowledgeEntries = entriesToSave;
        try {
            Path filePath = Path.of(knowledgeFile.getURI());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), knowledgeEntries);
        } catch (IOException e) {
            log.error("Error al guardar el conocimiento en el archivo JSON: {}", e.getMessage(), e);
        }
    }
}