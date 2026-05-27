package com.testai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения testAI.
 * <p>
 * Запускает Spring Boot приложение, которое предоставляет REST API
 * для управления каталогом товаров с использованием PostgreSQL и Liquibase.
 * </p>
 *
 * @see com.testai.controller.ProductController
 * @see com.testai.service.ProductService
 * @see com.testai.repository.ProductRepository
 */
@SpringBootApplication
public class TestAiApplication {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки, передаваемые в Spring Boot
     */
    public static void main(String[] args) {
        SpringApplication.run(TestAiApplication.class, args);
    }

}
