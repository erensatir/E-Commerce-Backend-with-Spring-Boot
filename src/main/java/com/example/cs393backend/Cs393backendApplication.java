package com.example.cs393backend;

import com.example.cs393backend.dto.*;
import com.example.cs393backend.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Cs393backendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cs393backendApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserService userService, ProductService productService, OrderService orderService, AddressService addressService, ShoppingCartService shoppingCartService, ItemService itemService, ReviewService reviewService) {
		return (args) -> {
			// User Initialization
			UserDto user1 = new UserDto();
			user1.setUsername("user1");
			user1.setEmail("user1@example.com");
			user1.setPassword("password1");
			user1.setPhoneNumber("0123456789");
			user1.setRole("Admin");
			user1 = userService.createUser(user1);


			UserDto user2 = new UserDto();
			user2.setUsername("user2");
			user2.setEmail("user2@example.com");
			user2.setPassword("password2");
			user2.setPhoneNumber("98746543210");
			user2.setRole("User");
			user2 = userService.createUser(user2);

			// Product Initialization
			ProductDto product1 = new ProductDto();
			product1.setName("Product 1");
			product1.setDescription("Description for Product 1");
			product1.setPrice(10.0);
			product1 = productService.createProduct(product1);

			ProductDto product2 = new ProductDto();
			product2.setName("Product 2");
			product2.setDescription("Description for Product 2");
			product2.setPrice(20.0);
			product2 = productService.createProduct(product2);

			// Address Initialization
			AddressDto address1 = new AddressDto();
			address1.setStreet("Street 1");
			address1.setCity("City 1");
			address1.setState("State 1");
			address1.setZip("Zip 1");
			address1 = addressService.createAddress(address1);

			AddressDto address2 = new AddressDto();
			address2.setStreet("Street 2");
			address2.setCity("City 2");
			address2.setState("State 2");
			address2.setZip("Zip 2");
			address2 = addressService.createAddress(address2);

			// Order Initialization
			OrderDto order1 = new OrderDto();
			order1.setUserId(user1.getId());
			order1.setTotalAmount(new BigDecimal("100.00"));
			order1.setOrderDate(new Date());
			order1.setAddressId(address1.getId());
			order1.setProductIds(Arrays.asList(product1.getId(), product2.getId()));
			order1 = orderService.createOrder(order1);

			OrderDto order2 = new OrderDto();
			order2.setUserId(user2.getId());
			order2.setTotalAmount(new BigDecimal("150.00"));
			order2.setOrderDate(new Date());
			order2.setAddressId(address2.getId());
			order2.setProductIds(Collections.singletonList(product2.getId()));
			order2 = orderService.createOrder(order2);

			// ShoppingCart Initialization
			ShoppingCartDto shoppingCart1 = new ShoppingCartDto();
			shoppingCart1.setUserId(user1.getId());
			shoppingCart1 = shoppingCartService.createShoppingCart(shoppingCart1);

			ShoppingCartDto shoppingCart2 = new ShoppingCartDto();
			shoppingCart2.setUserId(user2.getId());
			shoppingCart2 = shoppingCartService.createShoppingCart(shoppingCart2);

			// Item Initialization
			ItemDto item1 = new ItemDto();
			item1.setProductId(product1.getId());
			item1.setQuantity(2);
			item1.setShoppingCartId(shoppingCart1.getId());
			item1 = itemService.createItem(item1);

			ItemDto item2 = new ItemDto();
			item2.setProductId(product2.getId());
			item2.setQuantity(3);
			item2.setShoppingCartId(shoppingCart2.getId());
			item2 = itemService.createItem(item2);

			// Review Initialization
			ReviewDto review1 = new ReviewDto();
			review1.setUserId(user1.getId());
			review1.setProductId(product1.getId());
			review1.setRating(5);
			review1.setComment("Great product!");
			review1 = reviewService.createReview(review1);

			ReviewDto review2 = new ReviewDto();
			review2.setUserId(user2.getId());
			review2.setProductId(product2.getId());
			review2.setRating(4);
			review2.setComment("Good, but could be better.");
			review2 = reviewService.createReview(review2);
		};
	}

}
