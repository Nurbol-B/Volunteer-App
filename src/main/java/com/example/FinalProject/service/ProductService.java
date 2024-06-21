package com.example.FinalProject.service;

import com.example.FinalProject.dto.ProductDto;
import com.example.FinalProject.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto addProduct(ProductDto productDto);
    void deleteProduct(Long id);
    ProductDto updateProduct(Long id, ProductDto productDto);
}
