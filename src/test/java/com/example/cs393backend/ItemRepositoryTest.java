package com.example.cs393backend;

import com.example.cs393backend.entity.ItemEntity;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.entity.ShoppingCartEntity;
import com.example.cs393backend.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    private ItemEntity createItem() {
        // Create a product
        ProductEntity product = new ProductEntity();
        product.setId(1L);

        // Create a shopping cart
        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
        shoppingCart.setId(1L);

        // Create an item
        ItemEntity item = new ItemEntity();
        item.setProduct(product);
        item.setShoppingCart(shoppingCart);
        item.setQuantity(5);

        return item;
    }

    @Test
    public void testCreateItem() {
        // Given
        ItemEntity item = createItem();

        // When
        ItemEntity savedItem = itemRepository.save(item);

        // Then
        assertNotNull(savedItem);
        assertNotNull(Optional.of(savedItem.getId()));
    }
    @Test
    public void testReadItem() {
        // Given
        ItemEntity item = createItem();
        ItemEntity savedItem = itemRepository.save(item);

        // When
        Optional<ItemEntity> foundItem = itemRepository.findById(savedItem.getId());

        // Then
        assertTrue(foundItem.isPresent());
        assertEquals(savedItem.getId(), foundItem.get().getId());
        assertEquals(savedItem.getProduct(), foundItem.get().getProduct());
        assertEquals(savedItem.getShoppingCart(), foundItem.get().getShoppingCart());
        assertEquals(savedItem.getQuantity(), foundItem.get().getQuantity());
    }

    @Test
    public void testUpdateItem() {
        // Given
        ItemEntity item = createItem();
        ItemEntity savedItem = itemRepository.save(item);
        savedItem.setQuantity(10);

        // When
        ItemEntity updatedItem = itemRepository.save(savedItem);

        // Then
        assertNotNull(updatedItem);
        assertEquals(10, updatedItem.getQuantity());
    }

    @Test
    public void testDeleteItem() {
        // Given
        ItemEntity item = createItem();
        ItemEntity savedItem = itemRepository.save(item);

        // When
        itemRepository.deleteById(savedItem.getId());

        // Then
        Optional<ItemEntity> deletedItem = itemRepository.findById(savedItem.getId());
        assertFalse(deletedItem.isPresent());
    }
}
