package com.example.cs393backend.service;

import com.example.cs393backend.dto.ProductDto;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.util.ProductMapper;
import com.example.cs393backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::productEntityToDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.productEntityToDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        ProductEntity productEntity = productMapper.productDtoToEntity(productDto);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return productMapper.productEntityToDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return productMapper.productEntityToDto(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    // Additional methods as per your requirements
}
