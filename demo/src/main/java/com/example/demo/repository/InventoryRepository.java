package com.example.demo.repository;

import com.example.demo.model.Inventory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public InventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 保存或更新庫存
    public void save(Inventory inventory) {
        String sql = "INSERT INTO Inventory (product_id, quantity, created_at, updated_at) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = VALUES(quantity), updated_at = VALUES(updated_at)";
        jdbcTemplate.update(sql, inventory.getProductId(), inventory.getQuantity(), inventory.getCreatedAt(), inventory.getUpdatedAt());
    }

    // 根據產品 ID 獲取庫存
    public Optional<Inventory> findByProductId(Long productId) {
        String sql = "SELECT * FROM Inventory WHERE product_id = ?";
        return jdbcTemplate.query(sql, new InventoryRowMapper(), productId)
                .stream().findFirst();
    }

    // 獲取所有庫存
    public List<Inventory> findAll() {
        String sql = "SELECT * FROM Inventory";
        return jdbcTemplate.query(sql, new InventoryRowMapper());
    }

    // 更新庫存數量
    public void updateQuantity(Long productId, int quantity) {
        String sql = "UPDATE Inventory SET quantity = ?, updated_at = CURRENT_TIMESTAMP WHERE product_id = ?";
        jdbcTemplate.update(sql, quantity, productId);
    }

    // 根據產品 ID 刪除庫存項目
    public void deleteByProductId(Long productId) {
        String sql = "DELETE FROM Inventory WHERE product_id = ?";
        jdbcTemplate.update(sql, productId);
    }

    // 根據 ID 刪除庫存項目
    public void deleteById(Long id) {
        String sql = "DELETE FROM Inventory WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // 自定義 RowMapper
    private static class InventoryRowMapper implements RowMapper<Inventory> {
        @Override
        public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {
            Inventory inventory = new Inventory();
            inventory.setId(rs.getLong("id"));
            inventory.setProductId(rs.getLong("product_id"));
            inventory.setQuantity(rs.getInt("quantity"));
            inventory.setCreatedAt(rs.getTimestamp("created_at"));
            inventory.setUpdatedAt(rs.getTimestamp("updated_at"));
            return inventory;
        }
    }
}
