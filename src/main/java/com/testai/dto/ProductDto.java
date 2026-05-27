package com.testai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) для товара.
 * <p>
 * Используется для передачи данных между слоями приложения
 * (контроллер → сервис → маппер). Содержит только необходимые
 * для клиента поля и аннотации валидации.
 * </p>
 *
 * @see com.testai.entity.Product
 * @see com.testai.mapper.ProductMapper
 */
public class ProductDto {

    /** Идентификатор товара (заполняется сервером при создании). */
    private Long id;

    /** Наименование товара. Обязательное поле. */
    @NotBlank(message = "Name is required")
    private String name;

    /** Описание товара. Необязательное поле. */
    private String description;

    /** Цена товара. Должна быть положительной или равной нулю. */
    @PositiveOrZero(message = "Price must be positive or zero")
    private BigDecimal price;

    /**
     * Конструктор по умолчанию.
     */
    public ProductDto() {
    }

    /**
     * Конструктор для создания DTO со всеми полями.
     *
     * @param id          идентификатор товара
     * @param name        наименование товара
     * @param description описание товара
     * @param price       цена товара
     */
    public ProductDto(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Возвращает идентификатор товара.
     *
     * @return идентификатор товара
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор товара.
     *
     * @param id идентификатор товара
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает наименование товара.
     *
     * @return наименование товара
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает наименование товара.
     *
     * @param name наименование товара
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает описание товара.
     *
     * @return описание товара
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает описание товара.
     *
     * @param description описание товара
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает цену товара.
     *
     * @return цена товара
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Устанавливает цену товара.
     *
     * @param price цена товара (должна быть ≥ 0)
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
