package com.example.cs393backend.RepositoryTests;

import com.example.cs393backend.entity.ShoppingCartEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private ShoppingCartEntity createShoppingCart() {
        // Create a user
        UserEntity user = new UserEntity();
        user.setId(1L);

        // Create a shopping cart
        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
        shoppingCart.setUser(user);

        return shoppingCart;
    }



    @Test
    public void testCreateShoppingCart() {
        // Given
        ShoppingCartEntity shoppingCart = createShoppingCart();

        // When
        ShoppingCartEntity savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        // Then
        assertNotNull(savedShoppingCart);
        assertNotNull(savedShoppingCart.getId());
    }

    @Test
    public void testReadShoppingCart() {
        // Given
        ShoppingCartEntity shoppingCart = createShoppingCart();
        ShoppingCartEntity savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        // When
        Optional<ShoppingCartEntity> foundShoppingCart = shoppingCartRepository.findById(savedShoppingCart.getId());

        // Then
        assertTrue(foundShoppingCart.isPresent());
        assertEquals(savedShoppingCart.getId(), foundShoppingCart.get().getId());
        assertEquals(savedShoppingCart.getUser(), foundShoppingCart.get().getUser());
    }

    @Test
    public void testUpdateShoppingCart() {
        // Given
        ShoppingCartEntity shoppingCart = createShoppingCart();
        ShoppingCartEntity savedShoppingCart = shoppingCartRepository.save(shoppingCart);


        UserEntity newUser = new UserEntity();
        newUser.setId(2L); // assuming user with id 2 exists
        savedShoppingCart.setUser(newUser);

        // When
        ShoppingCartEntity updatedShoppingCart = shoppingCartRepository.save(savedShoppingCart);

        // Then
        assertNotNull(updatedShoppingCart);
        assertEquals(newUser, updatedShoppingCart.getUser());
    }

    @Test
    public void testDeleteShoppingCart() {
        // Given
        ShoppingCartEntity shoppingCart = createShoppingCart();
        ShoppingCartEntity savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        // When
        shoppingCartRepository.deleteById(savedShoppingCart.getId());

        // Then
        Optional<ShoppingCartEntity> deletedShoppingCart = shoppingCartRepository.findById(savedShoppingCart.getId());
        assertFalse(deletedShoppingCart.isPresent());
    }
}
