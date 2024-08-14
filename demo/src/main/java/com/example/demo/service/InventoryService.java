package com.example.demo.service;

import com.example.demo.model.Inventory;
import com.example.demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // 保存或更新庫存
    public void saveOrUpdateInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    // 根據產品ID獲取庫存
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId).orElse(null);
    }

    // 獲取所有庫存
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // 更新庫存數量
    public void updateInventoryQuantity(Long productId, int quantity) {
        inventoryRepository.updateQuantity(productId, quantity);
    }

    // 刪除庫存記錄
    public void deleteInventoryByProductId(Long productId) {
        inventoryRepository.deleteByProductId(productId);
    }
}
