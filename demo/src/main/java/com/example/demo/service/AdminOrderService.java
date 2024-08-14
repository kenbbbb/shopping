package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    // 獲取所有訂單
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 根據 ID 獲取訂單
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // 更新訂單狀態
    public boolean updateOrderStatus(Long id, String status) {
        if (orderRepository.existsById(id)) {
            Order existingOrder = orderRepository.findById(id).orElse(null);
            if (existingOrder != null) {
                existingOrder.setStatus(status);
                orderRepository.save(existingOrder);
                return true;
            }
        }
        return false;
    }

    // 刪除訂單
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
