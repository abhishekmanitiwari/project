package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest request);

    Product getProductById(Long id);

    Product updateProduct(ProductUpdateRequest request, Long productId);

    void deleteProduct(Long id);
    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsCategoryAndBrand(String categoryName, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long coundProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedToProducts(List<Product> listProduct);

    ProductDto convertToDto(Product product);
}
