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

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

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
