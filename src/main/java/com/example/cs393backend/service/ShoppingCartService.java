package com.example.cs393backend.service;

import com.example.cs393backend.dto.ShoppingCartDto;
import com.example.cs393backend.entity.ShoppingCartEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.util.ShoppingCartMapper;
import com.example.cs393backend.repository.ShoppingCartRepository;
import com.example.cs393backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ShoppingCartMapper shoppingCartMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.shoppingCartMapper = shoppingCartMapper;
    }

    public List<ShoppingCartDto> findAll() {
        return shoppingCartRepository.findAll().stream()
                .map(shoppingCartMapper::shoppingCartEntityToDto)
                .collect(Collectors.toList());
    }

    public ShoppingCartDto findById(Long id) {
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));
        return shoppingCartMapper.shoppingCartEntityToDto(shoppingCart);
    }

    public ShoppingCartDto createShoppingCart(ShoppingCartDto shoppingCartDto) {
        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();

        // Set user for the shopping cart
        UserEntity user = userRepository.findById(shoppingCartDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        shoppingCart.setUser(user);

        // Save the new shopping cart
        ShoppingCartEntity savedCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.shoppingCartEntityToDto(savedCart);
    }

    public ShoppingCartDto updateShoppingCart(Long id, ShoppingCartDto shoppingCartDto) {
        ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));



        ShoppingCartEntity updatedCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.shoppingCartEntityToDto(updatedCart);
    }

    public void deleteShoppingCart(Long id) {
        if (!shoppingCartRepository.existsById(id)) {
            throw new RuntimeException("Shopping cart not found");
        }
        shoppingCartRepository.deleteById(id);
    }


}
