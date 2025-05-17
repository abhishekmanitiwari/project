package com.dailycodework.dreamshops.controller;


import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;
    @GetMapping("/{cardId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cardId) {
        try {
            Cart cart = cartService.getCart(cardId);
            return ResponseEntity.ok(new ApiResponse("Cart retrieved successfully", cart,"200"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Error retrieving cart: " + e.getMessage(), null, "500"));
        }
    }

    @DeleteMapping("/{cardId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cardId) {
        try {
            cartService.clearCart(cardId);
            return ResponseEntity.ok(new ApiResponse("Cart cleared successfully", null, "200"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Error removing the cart: " + e.getMessage(), null, "500"));
        }
    }


    @RequestMapping("/{cardId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cardId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cardId);
            return ResponseEntity.ok(new ApiResponse("Total amount retrieved successfully", totalPrice, "200"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Error retrieving total amount: " + e.getMessage(), null, "500"));
        }

    }





}
