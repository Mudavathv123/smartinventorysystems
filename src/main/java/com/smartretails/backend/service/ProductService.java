package com.smartretails.backend.service;


import org.springframework.data.domain.Page;

import com.smartretails.backend.entity.Product;

public interface ProductService {
    Page<Product> getProducts(int page, int size);

    Page<Product> searchProduct(String sku, String name, int page, int size);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    Product getProductById(Long id);

}
