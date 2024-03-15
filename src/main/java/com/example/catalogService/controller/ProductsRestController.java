package com.example.catalogService.controller;

import com.example.catalogService.entity.Product;
import com.example.catalogService.payload.NewProductPayload;
import com.example.catalogService.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalog-api/products")
public class ProductsRestController {
    private final ProductService productService;

    @GetMapping
    public Iterable<Product> allProducts(@RequestParam(value = "filter", required = false) String filter) {
        System.out.println(filter);
        return productService.findAllProducts(filter);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Product product = productService.save(payload.title(), payload.details());
            return ResponseEntity
                    .created(
                            uriComponentsBuilder.replacePath("/catalog-api/products/{productId}").build(Map.of("productId", product.getId()))
                    )
                    .body(product);
        }
    }
}
