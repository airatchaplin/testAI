---
name: java-testing
description: Create unit and integration tests for Java using JUnit 5, AssertJ, and Mockito. Use when writing or fixing tests.
---

## Testing Specifications
- **Style**: AAA (Arrange, Act, Assert).
- **Mocks**: Use `@ExtendWith(MockitoExtension.class)`, `@Mock` and `@InjectMocks`.
- **Assertions**: Prefer AssertJ for fluent assertions.

## Test Template
\```java
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
@Mock private ProductRepository repository;
@InjectMocks private ProductService service;

    @Test
    @DisplayName("Should return product when ID exists")
    void testFindById_Success() {
        // Arrange
        given(repository.findById(1L)).willReturn(Optional.of(new Product()));

        // Act
        var result = service.findById(1L);

        // Assert
        assertThat(result).isNotNull();
    }
}
\```