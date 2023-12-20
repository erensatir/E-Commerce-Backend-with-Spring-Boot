package com.example.cs393backend.RepositoryTests;


import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByName_thenReturnProduct(){

        ProductEntity product = new ProductEntity();
        product.setName("test");
        product.setDescription("test");
        product.setPrice(10.0);
        entityManager.persist(product);
        entityManager.flush();

        ProductEntity found = productRepository.findByName(product.getName());

        assertThat(found.getName())
                .isEqualTo(product.getName());

    }

    @Test
    public void whenProductSaved_thenProductShouldBeSaved(){
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.0);

        productRepository.save(product);
        assertThat(productRepository.findByName(product.getName()).getDescription())
                .isEqualTo(product.getDescription());
    }

    @Test
    public void testFindProductById() {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.0);

        entityManager.persist(product);
        entityManager.flush();

        ProductEntity foundProduct = productRepository.findById(product.getId()).orElse(null);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo(product.getName());
        assertThat(foundProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void testUpdateProduct() {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.0);

        entityManager.persist(product);
        entityManager.flush();

        product.setName("Updated Product");
        entityManager.persist(product);
        entityManager.flush();

        ProductEntity foundProduct = productRepository.findById(product.getId()).orElse(null);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("Updated Product");
        assertThat(foundProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.0);

        entityManager.persist(product);
        entityManager.flush();

        productRepository.delete(product);

        ProductEntity foundProduct = productRepository.findById(product.getId()).orElse(null);

        assertThat(foundProduct).isNull();
    }

}
