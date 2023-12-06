package com.example.cs393backend;

import com.example.cs393backend.entity.*;
import com.example.cs393backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class Cs393backendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cs393backendApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository, AddressRepository addressRepository, ShoppingCartRepository shoppingCartRepository, ItemRepository itemRepository) {
		return (args) -> {
			// Create initial users
			UserEntity user1 = new UserEntity();
			user1.setUsername("user1");
			user1.setEmail("user1@example.com");
			user1 = userRepository.save(user1);

			UserEntity user2 = new UserEntity();
			user2.setUsername("user2");
			user2.setEmail("user2@example.com");
			user2 = userRepository.save(user2);

			// Create initial products
			ProductEntity product1 = new ProductEntity();
			product1.setName("Product 1");
			product1.setDescription("Description for Product 1");
			product1.setPrice(10.0);
			product1 = productRepository.save(product1);

			ProductEntity product2 = new ProductEntity();
			product2.setName("Product 2");
			product2.setDescription("Description for Product 2");
			product2.setPrice(20.0);
			product2 = productRepository.save(product2);

			// Create initial shopping carts and associate them with users
			ShoppingCartEntity shoppingCart1 = new ShoppingCartEntity();
			shoppingCart1.setUser(user1);
			shoppingCart1 = shoppingCartRepository.save(shoppingCart1);

			ShoppingCartEntity shoppingCart2 = new ShoppingCartEntity();
			shoppingCart2.setUser(user2);
			shoppingCart2 = shoppingCartRepository.save(shoppingCart2);

			// Create initial items and associate them with shopping carts and products
			ItemEntity item1 = new ItemEntity();
			item1.setProduct(product1);
			item1.setQuantity(1);
			item1.setShoppingCart(shoppingCart1);
			item1 = itemRepository.save(item1);

			ItemEntity item2 = new ItemEntity();
			item2.setProduct(product2);
			item2.setQuantity(2);
			item2.setShoppingCart(shoppingCart2);
			item2 = itemRepository.save(item2);

			// Create initial orders, associate them with users and addresses, and add products to them
			OrderEntity orderEntity1 = new OrderEntity();
			orderEntity1.setUser(user1);
			orderEntity1.setTotalAmount(new BigDecimal(10));
			orderEntity1.setProducts(Arrays.asList(product1));
			orderEntity1 = orderRepository.save(orderEntity1);

			OrderEntity orderEntity2 = new OrderEntity();
			orderEntity2.setUser(user2);
			orderEntity2.setTotalAmount(new BigDecimal(40));
			orderEntity2.setProducts(Arrays.asList(product1, product2));
			orderEntity2 = orderRepository.save(orderEntity2);

			// Create initial addresses and associate them with orders
			AddressEntity address1 = new AddressEntity();
			address1.setStreet("Street 1");
			address1.setCity("City 1");
			address1.setState("State 1");
			address1.setZip("Zip 1");
			address1.setOrderEntities(Arrays.asList(orderEntity1));
			address1 = addressRepository.save(address1);

			AddressEntity address2 = new AddressEntity();
			address2.setStreet("Street 2");
			address2.setCity("City 2");
			address2.setState("State 2");
			address2.setZip("Zip 2");
			address2.setOrderEntities(Arrays.asList(orderEntity2));
			address2 = addressRepository.save(address2);
		};
	}
}
