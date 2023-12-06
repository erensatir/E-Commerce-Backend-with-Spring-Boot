package com.example.cs393backend.service;

import com.example.cs393backend.dto.ShoppingCartDto;
import com.example.cs393backend.entity.ShoppingCartEntity;
import com.example.cs393backend.repository.ShoppingCartRepository;
import com.example.cs393backend.util.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    public ShoppingCartDto createShoppingCart(ShoppingCartDto shoppingCartDto) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartMapper.toEntity(shoppingCartDto);
        shoppingCartRepository.save(shoppingCartEntity);
        return shoppingCartMapper.toDto(shoppingCartEntity);
    }

    public ShoppingCartDto getShoppingCart(Long id) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found"));
        return shoppingCartMapper.toDto(shoppingCartEntity);
    }

    public ShoppingCartDto updateShoppingCart(ShoppingCartDto shoppingCartDto) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(shoppingCartDto.getId())
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found"));
        shoppingCartMapper.updateShoppingCartFromDto(shoppingCartDto, shoppingCartEntity);
        shoppingCartRepository.save(shoppingCartEntity);
        return shoppingCartMapper.toDto(shoppingCartEntity);
    }

    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }
}
