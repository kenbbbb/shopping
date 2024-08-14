package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 根據用戶名獲取所有訂單
    public List<Order> getAllOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    // 根據用戶名和訂單 ID 獲取訂單
    public Order getOrderById(String username, Long orderId) {
        Optional<Order> order = orderRepository.findByUsernameAndId(username, orderId);
        return order.orElse(null);
    }

    // 創建訂單
    public void createOrder(String username, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUsername(username);
        orderRepository.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(order);  // 使用 setOrder 方法
            orderItemRepository.save(item);
        }
    }

    // 刪除訂單
    public boolean deleteOrder(String username, Long orderId) {
        Optional<Order> orderOptional = orderRepository.findByUsernameAndId(username, orderId);
        if (orderOptional.isPresent()) {
            orderItemRepository.deleteByOrderId(orderId);
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
}
