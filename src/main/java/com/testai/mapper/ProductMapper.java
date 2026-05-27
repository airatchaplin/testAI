package com.testai.mapper;

import com.testai.dto.ProductDto;
import com.testai.entity.Product;
import org.springframework.stereotype.Component;

/**
 * Маппер для преобразования между сущностью {@link Product} и DTO {@link ProductDto}.
 * <p>
 * Отвечает за преобразование данных между слоем базы данных (Entity)
 * и слоем представления (DTO), изолируя клиента от внутренней структуры БД.
 * </p>
 *
 * @see Product
 * @see ProductDto
 */
@Component
public class ProductMapper {

    /**
     * Преобразует сущность {@link Product} в DTO {@link ProductDto}.
     *
     * @param product сущность товара (не может быть {@code null})
     * @return DTO товара со всеми заполненными полями
     */
    public ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }

    /**
     * Преобразует DTO {@link ProductDto} в сущность {@link Product}.
     * <p>
     * <strong>Важно:</strong> {@code id} не копируется — это позволяет
     * использовать метод как для создания, так и для обновления сущности.
     * </p>
     *
     * @param dto DTO товара (не может быть {@code null})
     * @return сущность товара (без ID, для последующего сохранения)
     */
    public Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }

}
