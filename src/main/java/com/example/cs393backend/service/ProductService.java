package com.example.cs393backend.service;

import com.example.cs393backend.dto.ProductDto;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.repository.ProductRepository;
import com.example.cs393backend.util.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductDto createProduct(ProductDto productDto) {
        ProductEntity productEntity = productMapper.toEntity(productDto);
        productRepository.save(productEntity);
        return productMapper.toDto(productEntity);
    }

    public ProductDto getProduct(long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(productEntity);
    }

    public ProductDto updateProduct(ProductDto productDto) {
        ProductEntity productEntity = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateProductFromDto(productDto, productEntity);
        productRepository.save(productEntity);
        return productMapper.toDto(productEntity);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
