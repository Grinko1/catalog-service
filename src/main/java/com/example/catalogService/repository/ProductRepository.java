package com.example.catalogService.repository;



import com.example.catalogService.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    public List<Product> findAll();
    public Product createProduct(Product product);

    public Optional<Product> findById(Integer productId);

    public void deleteById(Integer id);
}
