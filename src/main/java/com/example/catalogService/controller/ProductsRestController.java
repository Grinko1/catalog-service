package com.example.catalogService.controller;

import com.example.catalogService.entity.Product;
import com.example.catalogService.payload.NewProductPayload;
import com.example.catalogService.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalog-api/products")
public class ProductsRestController {
    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping
    public List<Product> allProducts() {
        return productService.findAllProducts();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload, BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder, Locale locale) {
        if (bindingResult.hasErrors()) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, this.messageSource.getMessage("errors.400.title", new Object[0],"errors.400.title", locale));
            problemDetail.setProperty("errors",
                    bindingResult.getAllErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList());
            return ResponseEntity.badRequest().body(problemDetail);
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
