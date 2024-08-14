package com.example.demo.repository;

import com.example.demo.model.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(OrderItem orderItem) {
        String sql = "INSERT INTO OrderItems (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice());
    }

    public Optional<OrderItem> findById(Long id) {
        String sql = "SELECT * FROM OrderItems WHERE id = ?";
        return jdbcTemplate.query(sql, new OrderItemRowMapper(), id).stream().findFirst();
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        String sql = "SELECT * FROM OrderItems WHERE order_id = ?";
        return jdbcTemplate.query(sql, new OrderItemRowMapper(), orderId);
    }

    public void deleteByOrderId(Long orderId) {
        String sql = "DELETE FROM OrderItems WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }

    private static class OrderItemRowMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(rs.getLong("id"));
            orderItem.setOrderId(rs.getLong("order_id"));
            orderItem.setProductId(rs.getLong("product_id"));
            orderItem.setQuantity(rs.getInt("quantity"));
            orderItem.setPrice(rs.getDouble("price"));
            return orderItem;
        }
    }
}
