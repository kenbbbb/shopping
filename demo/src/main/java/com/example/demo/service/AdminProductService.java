package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    // 獲取所有產品
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 根據ID獲取產品
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // 保存新產品
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // 更新產品資料
    public boolean updateProduct(Long id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            Product existingProduct = productRepository.findById(id).orElse(null);
            if (existingProduct != null) {
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setDescription(updatedProduct.getDescription());
                existingProduct.setPrice(updatedProduct.getPrice());
                productRepository.save(existingProduct);
                return true;
            }
        }
        return false;
    }

    // 刪除產品
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
