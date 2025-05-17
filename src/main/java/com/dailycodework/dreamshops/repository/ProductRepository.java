package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Product;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.brand = :brand")
    List<Product> findByCategoryNameAndBrand(@Param("categoryName") String categoryName, @Param("brand") String brand);


    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);
}
