package com.testai.repository;

import com.testai.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA репозиторий для работы с сущностью {@link Product}.
 * <p>
 * Предоставляет стандартный набор CRUD-операций: поиск по ID,
 * сохранение, обновление, удаление, получение всех записей.
 * </p>
 *
 * @see Product
 * @see com.testai.service.ProductService
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
