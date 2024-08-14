package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 獲取所有訂單
    public List<Order> findAll() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    // 根據用戶名獲取所有訂單
    public List<Order> findAllByUsername(String username) {
        String sql = "SELECT * FROM orders WHERE username = ?";
        return jdbcTemplate.query(sql, new OrderRowMapper(), username);
    }

    // 根據用戶名和訂單ID獲取訂單
    public Optional<Order> findByUsernameAndId(String username, Long id) {
        String sql = "SELECT * FROM orders WHERE username = ? AND id = ?";
        List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper(), username, id);
        if (orders.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(orders.get(0));
        }
    }

    // 根據ID獲取訂單
    public Optional<Order> findById(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper(), id);
        if (orders.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(orders.get(0));
        }
    }

    // 檢查訂單是否存在
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM orders WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    // 保存訂單，並返回生成的主鍵（訂單ID）
    public Long save(Order order) {
        String sql = "INSERT INTO orders (username, status, created_at, updated_at) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getUsername());
            ps.setString(2, order.getStatus());
            ps.setTimestamp(3, order.getCreatedAt());
            ps.setTimestamp(4, order.getUpdatedAt());
            return ps;
        }, (PreparedStatement ps) -> {
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        });
    }
    

    // 刪除訂單
    public void deleteById(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // 自定義 RowMapper
    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setUsername(rs.getString("username"));
            order.setStatus(rs.getString("status"));
            order.setCreatedAt(rs.getTimestamp("created_at"));
            order.setUpdatedAt(rs.getTimestamp("updated_at"));
            return order;
        }
    }
}
