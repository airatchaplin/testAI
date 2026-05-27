package com.testai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * JPA-сущность, представляющая товар.
 * <p>
 * Хранится в таблице {@code products} базы данных PostgreSQL.
 * Содержит基本信息 о товаре: наименование, описание и цену.
 * </p>
 *
 * @see com.testai.dto.ProductDto
 * @see com.testai.repository.ProductRepository
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Наименование товара. Не может быть пустым. */
    @NotBlank
    private String name;

    /** Описание товара. Может быть {@code null} или пустым. */
    private String description;

    /** Цена товара. Должна быть положительной или равной нулю. */
    @PositiveOrZero
    private BigDecimal price;

    /**
     * Конструктор по умолчанию для JPA.
     */
    public Product() {
    }

    /**
     * Конструктор для создания товара со всеми полями.
     *
     * @param id          уникальный идентификатор товара
     * @param name        наименование товара
     * @param description описание товара
     * @param price       цена товара
     */
    public Product(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Возвращает уникальный идентификатор товара.
     *
     * @return идентификатор товара
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор товара.
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
     * @return описание товара или {@code null}, если не задано
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
