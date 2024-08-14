package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 保存用戶
    public void save(User user) {
        String sql = "INSERT INTO User (username, password, email, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt());
    }

    // 檢查用戶名是否已存在
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    // 獲取所有用戶
    public List<User> findAll() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // 根據 ID 獲取用戶
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), id)
                .stream().findFirst();
    }
     // 根據用戶名查找用戶
     public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), username)
                .stream().findFirst();
    }
    // 檢查用戶是否存在
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM User WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    // 刪除用戶
    public void deleteById(Long id) {
        String sql = "DELETE FROM User WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // 自定義 RowMapper
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setCreatedAt(rs.getTimestamp("created_at"));
            user.setUpdatedAt(rs.getTimestamp("updated_at"));
            return user;
        }
    }
}
