package com.example.demo.repository;

import com.example.demo.model.CartItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CartItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 保存購物車項目
    public void save(CartItem cartItem) {
        String sql = "INSERT INTO CartItem (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, cartItem.getCartId(), cartItem.getProduct().getId(), cartItem.getQuantity(), cartItem.getPrice());
    }

    // 根據ID查詢購物車項目
    public Optional<CartItem> findById(Long id) {
        String sql = "SELECT * FROM CartItem WHERE id = ?";
        return jdbcTemplate.query(sql, new CartItemRowMapper(), id)
                .stream().findFirst();
    }

    // 根據購物車ID查詢所有購物車項目
    public List<CartItem> findByCartId(Long cartId) {
        String sql = "SELECT * FROM CartItem WHERE cart_id = ?";
        return jdbcTemplate.query(sql, new CartItemRowMapper(), cartId);
    }

    // 更新購物車項目
    public void update(CartItem cartItem) {
        String sql = "UPDATE CartItem SET quantity = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, cartItem.getQuantity(), cartItem.getPrice(), cartItem.getId());
    }

    // 刪除購物車項目
    public void deleteById(Long id) {
        String sql = "DELETE FROM CartItem WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // 根據購物車ID刪除所有購物車項目
    public void deleteAllByCartId(Long cartId) {
        String sql = "DELETE FROM CartItem WHERE cart_id = ?";
        jdbcTemplate.update(sql, cartId);
    }

    // 自定義 RowMapper
    private static class CartItemRowMapper implements RowMapper<CartItem> {
        @Override
        public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            CartItem cartItem = new CartItem();
            cartItem.setId(rs.getLong("id"));
            cartItem.setCartId(rs.getLong("cart_id"));
            cartItem.setQuantity(rs.getInt("quantity"));
            cartItem.setPrice(rs.getDouble("price"));
            // 注意: Product 的映射需要額外處理
            return cartItem;
        }
    }
}
