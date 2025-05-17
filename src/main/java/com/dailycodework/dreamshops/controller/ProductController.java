package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedToProducts = productService.getConvertedToProducts(products);
        return ResponseEntity.ok(new ApiResponse("Products fetched successfully", convertedToProducts,"200"));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> findProductById(@PathVariable("productId") Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", productDto,"200"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error fetching product"+e.getMessage(), null,"500"));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Product added successfully", savedProduct,"201"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error adding product"+e.getMessage(), null,"500"));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductUpdateRequest product) {
        try {
            Product updatedProduct = productService.updateProduct(product, productId);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", updatedProduct,"200"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error updating product"+e.getMessage(), null,"500"));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null,"200"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error deleting product"+e.getMessage(), null,"500"));
        }
    }
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> findProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);

            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found", null,"404"));
            }
            //List<ProductDto> convertedToProducts = productService.getConvertedToProducts(products);
            var convertedToProducts = productService.getConvertedToProducts(products);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", convertedToProducts,"200"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error fetching product"+e.getMessage(), null,"500"));
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> findProductsByCategoryAndBrand(@RequestBody Category category, @RequestParam String brandName) {
        try {
            List<Product> products = productService.getProductsCategoryAndBrand(category.getName(), brandName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found", null,"404"));
            }
            //List<ProductDto> convertedToProducts = productService.getConvertedToProducts(products);
            var convertedToProducts = productService.getConvertedToProducts(products);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", products,"200"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error fetching product"+e.getMessage(), null,"500"));
        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> findProductByName(@PathVariable("name") String productName) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found", null,"404"));
            }
            var convertedToProducts = productService.getConvertedToProducts(products);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", convertedToProducts,"200"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error fetching product"+e.getMessage(), null,"500"));
        }
    }

    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> findProductsByBrand(@RequestParam String brand) {


        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found", null,"404"));
            }
            var convertedToProducts = productService.getConvertedToProducts(products);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", convertedToProducts,"200"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error fetching product"+e.getMessage(), null,"500"));
        }
    }

    @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found", null,"404"));
            }
            var convertedToProducts = productService.getConvertedToProducts(products);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", convertedToProducts,"200"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error fetching product"+e.getMessage(), null,"500"));
        }
    }

    @GetMapping("/products/count/by-brand-name/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            Long count = productService.coundProductsByBrandAndName(brandName, productName);
            return ResponseEntity.ok(new ApiResponse("Product count fetched successfully", count, "200"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error fetching product" + e.getMessage(), null, "500"));
        }

    }
}
