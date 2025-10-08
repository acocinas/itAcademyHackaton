package com.hackaton.itacademy.infrastructure.console;

import com.hackaton.itacademy.domain.KnowledgeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final String SEPARATOR = "------------------------------------";
    private final KnowledgeService knowledgeService;
    private final ApplicationContext context;

    public ConsoleRunner(KnowledgeService knowledgeService, ApplicationContext context) {
        this.knowledgeService = knowledgeService;
        this.context = context;
    }

    @Override
    public void run(String... args) {
        System.out.println(SEPARATOR);
        System.out.println("Bienvenido al Asistente Conversacional.");
        System.out.println("Escribe tu pregunta o 'salir' para terminar.");
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);
        String userInput;

        do {
            System.out.print("Tú: ");
            userInput = scanner.nextLine();

            if (!userInput.equalsIgnoreCase("salir")) {
                Optional<String> answer = knowledgeService.findAnswerByQuestion(userInput);

                if (answer.isPresent()) {
                    System.out.println("Bot: " + answer.get());
                } else {
                    System.out.println("Bot: Lo siento, no tengo respuesta para esa pregunta.");
                }
            }

        } while (!userInput.equalsIgnoreCase("salir"));

        System.out.println(SEPARATOR);
        System.out.println("¡Hasta pronto!");
        scanner.close();

        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}