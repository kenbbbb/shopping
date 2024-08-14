package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 獲取用戶的購物車
    @GetMapping
    public ResponseEntity<Cart> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName()); // 使用用戶ID作為範例
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // 向購物車添加商品
    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestBody CartItem cartItem) {
        cartService.addItemToCart(cartItem);
        return ResponseEntity.ok("Item added to cart successfully.");
    }

    // 更新購物車中的商品
    @PutMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestBody CartItem cartItem) {
        cartService.updateCartItem(cartItem);
        return ResponseEntity.ok("Cart item updated successfully.");
    }

    // 從購物車中移除商品
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long itemId) {
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok("Item removed from cart successfully.");
    }

    // 清空購物車
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName()); // 使用用戶ID作為範例
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully.");
    }
}
