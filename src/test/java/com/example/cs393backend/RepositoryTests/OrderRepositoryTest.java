package com.example.cs393backend.RepositoryTests;

import com.example.cs393backend.entity.AddressEntity;
import com.example.cs393backend.entity.OrderEntity;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        // Given
        OrderEntity orderEntity = createOrderEntity();

        // When
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // Then
        assertNotNull(savedOrder.getId());
    }

    @Test
    public void testFindOrderById() {
        // Given
        OrderEntity orderEntity = createOrderEntity();
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // When
        OrderEntity foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        // Then
        assertNotNull(foundOrder);
        assertEquals(savedOrder.getId(), foundOrder.getId());
    }

    @Test
    public void testUpdateOrder() {
        // Given
        OrderEntity orderEntity = createOrderEntity();
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // When
        savedOrder.setTotalAmount(new BigDecimal("150.00"));
        OrderEntity updatedOrder = orderRepository.save(savedOrder);

        // Then
        assertEquals(new BigDecimal("150.00"), updatedOrder.getTotalAmount());
        for (ProductEntity product : updatedOrder.getProducts()) {
            assertEquals(product.getPrice(), product.getPrice());
        }
    }

    @Test
    public void testDeleteOrder() {
        // Given
        OrderEntity orderEntity = createOrderEntity();
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // When
        orderRepository.deleteById(savedOrder.getId());

        // Then
        Optional<OrderEntity> deletedOrder = orderRepository.findById(savedOrder.getId());
        assertFalse(deletedOrder.isPresent());
    }

    private OrderEntity createOrderEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(new Date());
        orderEntity.setTotalAmount(new BigDecimal("100.00"));

        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@example.com");
        orderEntity.setUser(user);

        ProductEntity product1 = new ProductEntity();
        product1.setName("test_product1");
        product1.setPrice(15.0);
        product1.setDescription("test1");

        ProductEntity product2 = new ProductEntity();
        product2.setName("test_product2");
        product2.setPrice(25.0);
        product2.setDescription("test2");
        List<ProductEntity> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        orderEntity.setProducts(products);

        AddressEntity address = new AddressEntity();
        address.setZip("test");
        address.setCity("test");
        address.setState("test");
        address.setStreet("test");
        address = entityManager.persist(address);
        orderEntity.setAddress(address);

        return orderEntity;
    }


}
