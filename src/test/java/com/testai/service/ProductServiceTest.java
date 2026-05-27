package com.testai.service;

import com.testai.dto.ProductDto;
import com.testai.entity.Product;
import com.testai.exception.ResourceNotFoundException;
import com.testai.mapper.ProductMapper;
import com.testai.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDto dto;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Test Product", "Description", BigDecimal.valueOf(100.0));
        dto = new ProductDto(1L, "Test Product", "Description", BigDecimal.valueOf(100.0));
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        List<ProductDto> result = productService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Test Product");
    }

    @Test
    void findById_shouldReturnProduct_whenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        ProductDto result = productService.findById(1L);

        assertThat(result.getName()).isEqualTo("Test Product");
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnProduct() {
        ProductDto inputDto = new ProductDto(null, "New", "Desc", BigDecimal.valueOf(50.0));
        Product entity = new Product(null, "New", "Desc", BigDecimal.valueOf(50.0));
        Product saved = new Product(2L, "New", "Desc", BigDecimal.valueOf(50.0));
        ProductDto outputDto = new ProductDto(2L, "New", "Desc", BigDecimal.valueOf(50.0));

        when(productMapper.toEntity(inputDto)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(saved);
        when(productMapper.toDto(saved)).thenReturn(outputDto);

        ProductDto result = productService.create(inputDto);

        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("New");
    }

    @Test
    void update_shouldUpdateAndReturnProduct() {
        ProductDto updateDto = new ProductDto(null, "Updated", "New Desc", BigDecimal.valueOf(200.0));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(updateDto);

        ProductDto result = productService.update(1L, updateDto);

        assertThat(result.getName()).isEqualTo("Updated");
        assertThat(result.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(200.0));
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(99L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void delete_shouldDelete_whenExists() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.delete(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        when(productRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> productService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

}
