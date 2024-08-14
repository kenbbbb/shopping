package com.example.demo.model;

public class OrderItem {

    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
    private double price;

    // Constructors, Getters, and Setters
    public OrderItem() {
    }

    public OrderItem(Long id, Long orderId, Long productId, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // 新增的 setOrder 方法
    public void setOrder(Order order) {
        this.orderId = order.getId();
    }
}
