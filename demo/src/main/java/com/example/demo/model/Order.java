package com.example.demo.model;

import java.sql.Timestamp;
import java.util.List;

public class Order {

    private Long id;
    private String username;
    private String status;
    private List<OrderItem> items;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors, Getters, and Setters
    public Order() {
    }

    public Order(Long id, String username, String status, List<OrderItem> items, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.items = items;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
