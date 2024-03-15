package com.example.catalogService.service;


import com.example.catalogService.entity.Product;
import com.example.catalogService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Iterable<Product> findAllProducts(String filter) {
        if(filter != null && !filter.isBlank()){
            System.out.println("result with filter");
            return productRepository.findAllByTitleLikeIgnoreCase("%" +filter +"%");

        }else{
            System.out.println("usual get all");
            return productRepository.findAll();
        }

    }

    @Override
    @Transactional
    public Product save(String title, String details) {
        return productRepository.save(new Product(null, title, details));
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(Integer id, String title, String details) {
        this.productRepository.findById(id)
                .ifPresentOrElse(
                        product -> {
                            product.setTitle(title);
                            product.setDetails(details);
                            this.productRepository.save(product);
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
