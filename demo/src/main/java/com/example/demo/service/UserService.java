package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 保存用戶（註冊新用戶）
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // 檢查用戶名是否已存在
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // 獲取所有用戶
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 根據 ID 獲取用戶
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // 更新用戶資料
    public boolean updateUser(Long id, User updatedUser) {
        if (userRepository.existsById(id)) {
            User existingUser = userRepository.findById(id).get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            userRepository.save(existingUser);
            return true;
        } else {
            return false;
        }
    }

    // 刪除用戶
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
