package com.smartretails.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartretails.backend.config.ApiResponse;
import com.smartretails.backend.config.PageResponse;
import com.smartretails.backend.entity.Product;
import com.smartretails.backend.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<Product>>> getProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> p = productRepository.getProducts(page, size);
        PageResponse<Product> resp = PageResponse.<Product>builder().content(p.getContent())
                .page(p.getNumber())
                .size(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .first(p.isFirst())
                .last(p.isLast())
                .empty(p.isEmpty())
                .build();
        return ResponseEntity.ok(ApiResponse.success(resp));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<Product>>> search(@RequestParam(required = false) String sku,
            @RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> p = productRepository.searchProduct(sku, name, page, size);
        PageResponse<Product> resp = PageResponse.<Product>builder().content(p.getContent())
                .page(p.getNumber())
                .size(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .first(p.isFirst())
                .last(p.isLast())
                .empty(p.isEmpty())
                .build();
        return ResponseEntity.ok(ApiResponse.success(resp));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productRepository.createProduct(product);
        return ResponseEntity.ok(ApiResponse.success("created", createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable("id") long id,
            @RequestBody Product product) {
        Product updatedProduct = productRepository.updateProduct(id, product);
        return ResponseEntity.ok(ApiResponse.success("Updated", updatedProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success(productRepository.getProductById(id)));
    }

}
