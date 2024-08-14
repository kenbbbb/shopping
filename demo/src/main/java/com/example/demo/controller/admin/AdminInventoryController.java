package com.example.demo.controller.admin;

import com.example.demo.model.Inventory;
import com.example.demo.service.AdminInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/inventory")
public class AdminInventoryController {

    @Autowired
    private AdminInventoryService adminInventoryService;

    // 獲取所有庫存
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventoryList = adminInventoryService.getAllInventory();
        return ResponseEntity.ok(inventoryList);
    }

    // 根據產品 ID 獲取庫存詳情
    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        Inventory inventory = adminInventoryService.getInventoryByProductId(productId);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 更新庫存數量
    @PutMapping("/{productId}/quantity")
    public ResponseEntity<String> updateInventoryQuantity(@PathVariable Long productId, @RequestParam int quantity) {
        boolean isUpdated = adminInventoryService.updateInventory(productId, quantity);
        if (isUpdated) {
            return ResponseEntity.ok("Inventory quantity updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 新增庫存項目
    @PostMapping
    public ResponseEntity<String> addInventory(@RequestBody Inventory inventory) {
        adminInventoryService.addInventory(inventory);
        return ResponseEntity.ok("Inventory added successfully.");
    }

    // 刪除庫存項目
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long productId) {
        boolean isDeleted = adminInventoryService.deleteInventory(productId);
        if (isDeleted) {
            return ResponseEntity.ok("Inventory deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
