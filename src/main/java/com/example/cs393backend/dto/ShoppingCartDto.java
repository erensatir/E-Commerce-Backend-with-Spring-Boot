package com.example.cs393backend.dto;

import java.util.List;

public class ShoppingCartDto {
    private Long id;
    private Long userId; // User's ID associated with the shopping cart
    private List<Long> itemIds; // List of item IDs in the shopping cart

    // Constructors
    public ShoppingCartDto() {}

    public ShoppingCartDto(Long id, Long userId, List<Long> itemIds) {
        this.id = id;
        this.userId = userId;
        this.itemIds = itemIds;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
