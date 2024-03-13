package com.example.catalogService.service;


import com.example.catalogService.entity.Product;
import com.example.catalogService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product save(String title, String details) {
        return productRepository.createProduct(new Product(null, title, details));
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    public void updateProduct(Integer id, String title, String details) {
        this.productRepository.findById(id)
                .ifPresentOrElse(
                        product -> {
                            product.setTitle(title);
                            product.setDetails(details);
                        }, () -> {
                            throw new NoSuchElementException();
                        }
                );
    }

    @Override
    public void deleteProduct(Integer id) {
        this.productRepository.deleteById(id);
    }


}
