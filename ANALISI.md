# Analysis and Technical Decisions

## Key Rules and Behaviors

The bot is designed as a simple conversational assistant that functions through a console interface and a REST API for knowledge base management.

* **Keyword-based search:** The bot uses a search logic that ignores capitalization, accents, and punctuation. To find an answer, it looks for user-provided keywords within its knowledge base.
* **Default behavior:** If no answer is found, the bot notifies the user with a predefined message.
* **Console and API:** Users can interact with the bot via the console to ask questions, and the knowledge base can be modified through a REST API.

## Knowledge Base Structure

The knowledge base is stored in a flat **JSON file** (`knowledge.json`), following a `question` and `answer` format. This structure is loaded at application startup to simulate persistence and is updated via the API.

## Architecture and Technical Decisions

* **Hexagonal Architecture:** The project follows a hexagonal architecture with three main layers to ensure a separation of concerns:
    * **`domain`**: Contains the pure business logic and the application's "ports" (interfaces and data models).
    * **`service`**: Holds the implementation of the domain's business logic.
    * **`infrastructure`**: Acts as the "adapters" layer, containing technology-specific implementations for persistence (JSON file), web (REST API), and console interactions.

* **Framework (Spring Boot):** Spring Boot was chosen for its ease of configuration, dependency injection, and management of the application lifecycle.
* **Tests:** Both unit tests (for the service) and integration tests (for the controller) have been implemented to ensure code quality and reliability.
* **Java Version:** Java 21, the latest LTS version, was used to leverage its new features.
* **API Documentation:** The API is documented with `springdoc-openapi` (Swagger), which allows developers to explore and test the endpoints interactively.

## Considerations for Future Maintenance

* **Multi-language support:** A system could be implemented to handle different JSON files per language.
* **Real database:** In a production environment, the JSON file would be replaced with a relational (like PostgreSQL) or non-relational (like MongoDB) database.