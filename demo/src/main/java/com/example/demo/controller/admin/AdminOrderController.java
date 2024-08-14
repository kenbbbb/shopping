package com.example.demo.controller.admin;

import com.example.demo.model.Order;
import com.example.demo.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    // 獲取所有訂單
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = adminOrderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // 根據訂單 ID 獲取訂單詳情
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = adminOrderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 更新訂單狀態
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        boolean isUpdated = adminOrderService.updateOrderStatus(orderId, status);
        if (isUpdated) {
            return ResponseEntity.ok("Order status updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除訂單
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        boolean isDeleted = adminOrderService.deleteOrder(orderId);
        if (isDeleted) {
            return ResponseEntity.ok("Order deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
