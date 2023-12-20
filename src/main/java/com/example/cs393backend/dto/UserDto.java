package com.example.cs393backend.dto;

// UserDto.java
public class UserDto {
    private Long id;
    private String username;
    private String email;
    // Password is typically not included in DTO for security reasons
    // But you can include it if it's necessary for your business logic

    // Constructors
    public UserDto() {
    }

    public UserDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Optional: Override toString, equals, and hashCode methods as per your requirements
}
