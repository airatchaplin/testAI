package com.testai.service;

import com.testai.dto.ProductDto;
import com.testai.entity.Product;
import com.testai.exception.ResourceNotFoundException;
import com.testai.mapper.ProductMapper;
import com.testai.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для управления товарами.
 * <p>
 * Содержит бизнес-логику CRUD-операций: получение списка товаров,
 * поиск по ID, создание, обновление и удаление. Все публичные методы
 * логируют вход и выход для упрощения отладки и мониторинга.
 * </p>
 *
 * @see ProductController
 * @see ProductRepository
 * @see ProductMapper
 */
@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param productRepository репозиторий для работы с товарами
     * @param productMapper     маппер для преобразования Entity ↔ DTO
     */
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Возвращает список всех товаров.
     *
     * @return список DTO всех товаров (может быть пустым)
     */
    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        log.info("→ findAll started");
        try {
            List<ProductDto> result = productRepository.findAll()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
            log.info("← findAll completed - resultSize={}", result.size());
            return result;
        } catch (Exception e) {
            log.error("✗ findAll failed - error={}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Возвращает товар по его идентификатору.
     *
     * @param id идентификатор товара (не может быть {@code null})
     * @return DTO найденного товара
     * @throws ResourceNotFoundException если товар с указанным ID не найден
     */
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        log.info("→ findById started - id={}", id);
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            ProductDto result = productMapper.toDto(product);
            log.info("← findById completed - result={}", result);
            return result;
        } catch (Exception e) {
            log.error("✗ findById failed - id={}, error={}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Создаёт новый товар.
     *
     * @param dto DTO с данными нового товара (наименование, описание, цена)
     * @return DTO созданного товара с присвоенным ID
     */
    @Transactional
    public ProductDto create(ProductDto dto) {
        log.info("→ create started - dto={}", dto);
        try {
            Product product = productMapper.toEntity(dto);
            Product saved = productRepository.save(product);
            ProductDto result = productMapper.toDto(saved);
            log.info("← create completed - result={}", result);
            return result;
        } catch (Exception e) {
            log.error("✗ create failed - dto={}, error={}", dto, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Обновляет существующий товар.
     * <p>
     * Находит товар по ID, обновляет его поля и сохраняет изменения.
     * </p>
     *
     * @param id  идентификатор обновляемого товара
     * @param dto DTO с новыми данными товара
     * @return DTO обновлённого товара
     * @throws ResourceNotFoundException если товар с указанным ID не найден
     */
    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        log.info("→ update started - id={}, dto={}", id, dto);
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            Product saved = productRepository.save(product);
            ProductDto result = productMapper.toDto(saved);
            log.info("← update completed - result={}", result);
            return result;
        } catch (Exception e) {
            log.error("✗ update failed - id={}, dto={}, error={}", id, dto, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Удаляет товар по его идентификатору.
     *
     * @param id идентификатор удаляемого товара
     * @throws ResourceNotFoundException если товар с указанным ID не найден
     */
    @Transactional
    public void delete(Long id) {
        log.info("→ delete started - id={}", id);
        try {
            if (!productRepository.existsById(id)) {
                throw new ResourceNotFoundException("Product not found with id: " + id);
            }
            productRepository.deleteById(id);
            log.info("← delete completed - id={}", id);
        } catch (Exception e) {
            log.error("✗ delete failed - id={}, error={}", id, e.getMessage(), e);
            throw e;
        }
    }

}
