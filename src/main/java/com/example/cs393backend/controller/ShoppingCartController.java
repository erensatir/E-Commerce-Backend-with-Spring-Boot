package com.example.cs393backend.controller;

import com.example.cs393backend.dto.ShoppingCartDto;
import com.example.cs393backend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public ShoppingCartDto createShoppingCart(@RequestBody ShoppingCartDto shoppingCartDto) {
        return shoppingCartService.createShoppingCart(shoppingCartDto);
    }

    @GetMapping("/{id}")
    public ShoppingCartDto getShoppingCart(@PathVariable Long id) {
        return shoppingCartService.getShoppingCart(id);
    }

    @PutMapping
    public ShoppingCartDto updateShoppingCart(@RequestBody ShoppingCartDto shoppingCartDto) {
        return shoppingCartService.updateShoppingCart(shoppingCartDto);
    }

    @DeleteMapping("/{id}")
    public void deleteShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteShoppingCart(id);
    }
}
