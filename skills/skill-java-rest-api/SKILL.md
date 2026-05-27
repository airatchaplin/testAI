---
name: java-rest-api
description: Build REST API using Spring Boot, including controllers, DTOs, services, and repositories. Use when building web endpoints.
---

## API Development Workflow
1. Define Entity and DTO (record/class).
2. Create Repository (JPA).
3. Implement Service with business logic.
4. Build RestController.
5. Add Exception Handling.

## Code Requirements
- Use `ResponseEntity` for HTTP responses.
- Follow `@Autowired` constructor injection.
- Validation with `@Valid` and Jakarta annotations.
- Document APIs using `OpenAPI 3` annotations.

## Example Entity
\```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private double price;
    // getters/setters
}
\```