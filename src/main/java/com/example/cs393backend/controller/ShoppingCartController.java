package com.example.cs393backend.controller;

import com.example.cs393backend.dto.ShoppingCartDto;
import com.example.cs393backend.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCarts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCartDto>> getAllShoppingCarts() {
        List<ShoppingCartDto> shoppingCarts = shoppingCartService.findAll();
        return ResponseEntity.ok(shoppingCarts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartDto> getShoppingCartById(@PathVariable Long id) {
        ShoppingCartDto shoppingCart = shoppingCartService.findById(id);
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping
    public ResponseEntity<ShoppingCartDto> createShoppingCart(@RequestBody ShoppingCartDto shoppingCartDto) {
        ShoppingCartDto newShoppingCart = shoppingCartService.createShoppingCart(shoppingCartDto);
        return ResponseEntity.ok(newShoppingCart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCartDto> updateShoppingCart(@PathVariable Long id, @RequestBody ShoppingCartDto shoppingCartDto) {
        ShoppingCartDto updatedShoppingCart = shoppingCartService.updateShoppingCart(id, shoppingCartDto);
        return ResponseEntity.ok(updatedShoppingCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteShoppingCart(id);
        return ResponseEntity.ok().build();
    }


}
