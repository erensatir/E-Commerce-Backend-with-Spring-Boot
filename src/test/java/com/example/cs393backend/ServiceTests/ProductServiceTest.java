
package com.example.cs393backend.ServiceTests;

import com.example.cs393backend.dto.ProductDto;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.repository.ProductRepository;
import com.example.cs393backend.service.ProductService;
import com.example.cs393backend.util.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private ProductDto productDto;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Test Product");
        productDto.setDescription("Description");
        productDto.setPrice(10.0);

        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Test Product");
        productEntity.setDescription("Description");
        productEntity.setPrice(10.0);
    }

    @Test
    void findAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(productEntity));
        when(productMapper.productEntityToDto(any(ProductEntity.class))).thenReturn(productDto);

        List<ProductDto> result = productService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(productDto, result.get(0));

        verify(productRepository).findAll();
        verify(productMapper).productEntityToDto(any(ProductEntity.class));
    }

    @Test
    void findProductById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));
        when(productMapper.productEntityToDto(any(ProductEntity.class))).thenReturn(productDto);

        ProductDto result = productService.findById(1L);

        assertNotNull(result);
        assertEquals(productDto, result);

        verify(productRepository).findById(anyLong());
        verify(productMapper).productEntityToDto(any(ProductEntity.class));
    }

    @Test
    void createProduct() {
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        when(productMapper.productDtoToEntity(any(ProductDto.class))).thenReturn(productEntity);
        when(productMapper.productEntityToDto(any(ProductEntity.class))).thenReturn(productDto);

        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result);
        assertEquals(productDto, result);

        verify(productRepository).save(any(ProductEntity.class));
        verify(productMapper).productDtoToEntity(any(ProductDto.class));
        verify(productMapper).productEntityToDto(any(ProductEntity.class));
    }

    @Test
    void updateProduct() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));
        when(productRepository.save(any(ProductEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));


        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto.setId(1L);
        updatedProductDto.setName("Updated Product");
        updatedProductDto.setDescription("Updated Description");
        updatedProductDto.setPrice(15.0);

        when(productMapper.productEntityToDto(any(ProductEntity.class))).thenReturn(updatedProductDto);
        ProductDto result = productService.updateProduct(1L, updatedProductDto);

        assertNotNull(result);
        assertEquals(updatedProductDto.getName(), result.getName());
        assertEquals(updatedProductDto.getDescription(), result.getDescription());
        assertEquals(updatedProductDto.getPrice(), result.getPrice());

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(ProductEntity.class));
        verify(productMapper).productEntityToDto(any(ProductEntity.class));
    }

    @Test
    void deleteProduct() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(anyLong());

        productService.deleteProduct(productId);

        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }
}