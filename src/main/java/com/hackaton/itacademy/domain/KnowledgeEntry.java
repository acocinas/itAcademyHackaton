package com.hackaton.itacademy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeEntry {

    private String question;
    private String answer;
}
