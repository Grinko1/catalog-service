package com.example.catalogService.service;



import com.example.catalogService.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> findAllProducts();
    public Product save(String title, String details);

   public Optional<Product> findProductById(int productId);

    void updateProduct(Integer id, String title, String details);

    void deleteProduct(Integer id);
}
