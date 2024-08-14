package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    // 獲取所有用戶
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 根據 ID 獲取用戶
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // 創建新用戶
    public void createUser(User user) {
        userRepository.save(user);
    }

    // 更新用戶資料
    public boolean updateUser(Long id, User updatedUser) {
        if (userRepository.existsById(id)) {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser != null) {
                existingUser.setUsername(updatedUser.getUsername());
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setRole(updatedUser.getRole());
                userRepository.save(existingUser);
                return true;
            }
        }
        return false;
    }

    // 刪除用戶
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
