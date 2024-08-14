package com.example.demo.service;

import com.example.demo.model.Inventory;
import com.example.demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // 獲取所有庫存
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // 根據產品 ID 獲取庫存
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId).orElse(null);
    }

    // 更新庫存數量
    public boolean updateInventory(Long productId, int newQuantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);
        if (inventory != null) {
            inventory.setQuantity(newQuantity);
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    }

    // 增加庫存項目
    public void addInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    // 刪除庫存項目
    public boolean deleteInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);
        if (inventory != null) {
            inventoryRepository.deleteById(inventory.getId());
            return true;
        }
        return false;
    }
}
