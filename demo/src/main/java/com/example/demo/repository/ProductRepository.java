package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 保存產品
    public void save(Product product) {
        String sql = "INSERT INTO Product (name, description, price, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getCreatedAt(), product.getUpdatedAt());
    }

    // 獲取所有產品
    public List<Product> findAll() {
        String sql = "SELECT * FROM Product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    // 根據 ID 獲取產品
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM Product WHERE id = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(), id)
                .stream().findFirst();
    }

    // 檢查產品是否存在
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM Product WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    // 刪除產品
    public void deleteById(Long id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // 自定義 RowMapper
    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setCreatedAt(rs.getTimestamp("created_at"));
            product.setUpdatedAt(rs.getTimestamp("updated_at"));
            return product;
        }
    }
}
