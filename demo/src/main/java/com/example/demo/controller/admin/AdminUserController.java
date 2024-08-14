package com.example.demo.controller.admin;

import com.example.demo.model.User;
import com.example.demo.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    // 獲取所有用戶
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 根據ID獲取用戶
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = adminUserService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 更新用戶資料
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (adminUserService.updateUser(id, updatedUser)) {
            return ResponseEntity.ok("用戶更新成功。");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除用戶
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (adminUserService.deleteUser(id)) {
            return ResponseEntity.ok("用戶刪除成功。");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
