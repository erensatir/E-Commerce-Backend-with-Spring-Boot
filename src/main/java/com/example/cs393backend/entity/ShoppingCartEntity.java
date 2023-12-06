package com.example.cs393backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shoppingCart")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne (mappedBy = "shoppingCart")
    private UserEntity user;

    @OneToMany (mappedBy = "shoppingCart")
    private List<ItemEntity> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }
}
