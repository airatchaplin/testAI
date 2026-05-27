package com.testai.controller;

import com.testai.dto.ProductDto;
import com.testai.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-контроллер для управления товарами.
 * <p>
 * Предоставляет полный набор CRUD-эндпоинтов по адресу {@code /api/products}.
 * Все методы возвращают {@link ResponseEntity} с соответствующим HTTP-статусом.
 * </p>
 *
 * <h3>Эндпоинты:</h3>
 * <ul>
 *   <li>{@code GET /api/products} — список всех товаров</li>
 *   <li>{@code GET /api/products/{id}} — товар по ID</li>
 *   <li>{@code POST /api/products} — создание товара</li>
 *   <li>{@code PUT /api/products/{id}} — обновление товара</li>
 *   <li>{@code DELETE /api/products/{id}} — удаление товара</li>
 * </ul>
 *
 * @see ProductService
 * @see com.testai.dto.ProductDto
 * @see com.testai.controller.advice.GlobalExceptionHandler
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Конструктор с внедрением зависимости сервиса товаров.
     *
     * @param productService сервис для работы с товарами
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Возвращает список всех товаров.
     *
     * @return HTTP-ответ 200 OK со списком товаров (может быть пустым)
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Возвращает товар по его идентификатору.
     *
     * @param id идентификатор товара
     * @return HTTP-ответ 200 OK с данными товара
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Создаёт новый товар.
     * <p>
     * Принимает JSON с данными товара в теле запроса.
     * Валидация выполняется автоматически благодаря аннотации {@code @Valid}.
     * </p>
     *
     * @param dto данные нового товара (наименование, описание, цена)
     * @return HTTP-ответ 201 Created с данными созданного товара
     */
    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto) {
        ProductDto created = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновляет существующий товар.
     *
     * @param id  идентификатор обновляемого товара
     * @param dto новые данные товара
     * @return HTTP-ответ 200 OK с обновлёнными данными товара
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    /**
     * Удаляет товар по его идентификатору.
     *
     * @param id идентификатор удаляемого товара
     * @return HTTP-ответ 204 No Content (тело ответа пустое)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
