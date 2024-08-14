package com.example.demo.controller.admin;

import com.example.demo.model.Product;
import com.example.demo.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    // 獲取所有產品
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = adminProductService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // 根據ID獲取產品
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = adminProductService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 創建新產品
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        adminProductService.saveProduct(product);
        return ResponseEntity.ok("產品創建成功。");
    }

    // 更新產品資料
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        if (adminProductService.updateProduct(id, updatedProduct)) {
            return ResponseEntity.ok("產品更新成功。");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除產品
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (adminProductService.deleteProduct(id)) {
            return ResponseEntity.ok("產品刪除成功。");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
