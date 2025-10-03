package com.smartretails.backend.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smartretails.backend.entity.Product;
import com.smartretails.backend.repository.ProductRepository;
import com.smartretails.backend.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productJpaRepository;

    @Override
    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return productJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchProduct(String sku, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return productJpaRepository.search(sku, name, pageable);
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        if (productJpaRepository.existsBySku(product.getSku())) {
            ;
            throw new ValidationException("Sku must be unique..");
        }

        return productJpaRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!!"));

        if (!existingProduct.getSku().equals(product.getSku()) && productJpaRepository.existsBySku(product.getSku())) {
            throw new ValidationException("Sku must be unique..");
        }

        existingProduct.setSku(product.getSku());
        existingProduct.setBarcode(product.getBarcode());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setIsActive(product.getIsActive());
        existingProduct.setName(product.getName());
        existingProduct.setUnitPrice(product.getUnitPrice());
        existingProduct.setTaxRate(product.getTaxRate());
        existingProduct.setReorderLevel(product.getReorderLevel());
        return productJpaRepository.save(existingProduct);
    }

    @Override
    public Product getProductById(Long id) {
        return productJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!!"));
    }

}
