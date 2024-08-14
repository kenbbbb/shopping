package com.example.demo.repository;

import com.example.demo.model.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 根據用戶ID獲取購物車
    public Optional<Cart> findByUserId(Long userId) {
        String sql = "SELECT * FROM Cart WHERE user_id = ?";
        return jdbcTemplate.query(sql, new CartRowMapper(), userId)
                .stream().findFirst();
    }

    // 保存購物車
    public void save(Cart cart) {
        String sql = "INSERT INTO Cart (user_id, created_at, updated_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, cart.getUserId(), cart.getCreatedAt(), cart.getUpdatedAt());
    }

    // 刪除購物車
    public void deleteById(Long id) {
        String sql = "DELETE FROM Cart WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // 自定義 RowMapper
    private static class CartRowMapper implements RowMapper<Cart> {
        @Override
        public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cart cart = new Cart();
            cart.setId(rs.getLong("id"));
            cart.setUserId(rs.getLong("user_id"));
            cart.setCreatedAt(rs.getTimestamp("created_at"));
            cart.setUpdatedAt(rs.getTimestamp("updated_at"));
            return cart;
        }
    }
}
