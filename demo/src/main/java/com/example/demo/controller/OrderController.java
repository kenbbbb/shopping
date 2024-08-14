package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 獲取用戶的所有訂單
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Order> orders = orderService.getAllOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }

    // 根據訂單 ID 獲取特定訂單
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Order order = orderService.getOrderById(username, orderId);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    // 創建訂單
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody List<OrderItem> orderItems) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        orderService.createOrder(username, orderItems);
        return ResponseEntity.ok("Order created successfully.");
    }

    // 刪除訂單
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean deleted = orderService.deleteOrder(username, orderId);
        return deleted ? ResponseEntity.ok("Order deleted successfully.") : ResponseEntity.notFound().build();
    }
}
